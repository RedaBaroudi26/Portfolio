package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories() ;

    Page<Category> getCategoriesByPage(int offset , int size) ;

    Category findCategoryById(Long idCategory) ;

    Long getCategoriesCount() ;

    Category addNewCategory(Category newCategory) ;

    Category updateCategory(Category category) ;

    void deleteCategory(Long idCategory) ;

}
