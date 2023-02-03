package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Category;
import com.smaaaak.Portfolio.repository.ArticleRepository;
import com.smaaaak.Portfolio.repository.CategoryRepository;
import com.smaaaak.Portfolio.share.ImageUtil;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository ;
    private ArticleRepository articleRepository ;
    private ServletContext context ;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository, ServletContext context) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.context = context;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll() ;
    }

    @Override
    public Page<Category> getCategoriesByPage(int offset , int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.categoryRepository.findAll(pageable) ;
    }

    @Override
    public Long getCategoriesCount() {
        return this.categoryRepository.count() ;
    }

    @Override
    public Category addNewCategory(Category newCategory) {
        if( this.categoryRepository.findCategoryByCategoryName(newCategory.getCategoryName()).isPresent() ){
            throw new ApiRequestException(" category already exists ") ;
        }
        this.createCategoryFile(newCategory);
        return this.categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(Category category) {

        Category oldestCategory ;

        if( !this.categoryRepository.findById(category.getIdCategory()).isPresent() ){
            throw new ApiRequestException(" category doesn't exists ") ;
        }

        if( this.categoryRepository.findCategoryByCategoryName(category.getCategoryName()).isPresent() ){
            throw new ApiRequestException(" category Name already exists ") ;
        }

        oldestCategory =  this.categoryRepository.findById(category.getIdCategory()).get() ;

        this.updateFileName(category , oldestCategory.getCategoryName());

        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long idCategory) {
        if(!this.categoryRepository.findById(idCategory).isPresent()){
            throw new ApiRequestException(" category doesn't exists ") ;
        }
        if(this.articleRepository.existsArticleByCategory_CategoryName(this.categoryRepository.findById(idCategory).get().getCategoryName())){
            throw new ApiRequestException(" can't delete this category , category still have articles ") ;
        }

        this.deleteCategoryFile(idCategory);
        this.categoryRepository.deleteById(idCategory);
    }

    private void createCategoryFile(Category category){
        String url = ( ImageUtil.ARTICLES  + category.getCategoryName() + "/" ).replaceAll("\\s+" , "" );
        boolean isExist = new File(url).exists();
        if (!isExist) {
            new File(context.getRealPath(url)).mkdir();
        }
    }

    private void updateFileName(Category category , String categoryName){
        String url = ( ImageUtil.ARTICLES + categoryName ).replaceAll("\\s+" , "" );
        String urlUpdated = ( ImageUtil.ARTICLES + category.getCategoryName() ) .replaceAll("\\s+", "") ;
        boolean isExist = new File(context.getRealPath(url)).exists();
        if (isExist) {
            Path source = Paths.get(context.getRealPath( url ));
            Path newSource = Paths.get(context.getRealPath( urlUpdated ));
            try {
                Files.move( source , newSource );
            } catch (IOException e) {
              throw new ApiRequestException(e.getMessage()) ;
            }
        }
    }

    private void deleteCategoryFile(Long idCategory){
        Category category = this.categoryRepository.findById(idCategory).get() ;
        String url = context.getRealPath(ImageUtil.ARTICLES + category.getCategoryName().replaceAll("\\s+" , "") + "/") ;
        boolean isExist = new File(url).exists();
        if (isExist) {
            new File(url).delete() ;
        }
    }

}
