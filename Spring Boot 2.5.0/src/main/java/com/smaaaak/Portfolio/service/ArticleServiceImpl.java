package com.smaaaak.Portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.Image;
import com.smaaaak.Portfolio.model.projection.ArticleProjection;
import com.smaaaak.Portfolio.repository.ArticleRepository;
import com.smaaaak.Portfolio.repository.ImageRepository;
import com.smaaaak.Portfolio.share.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository ;
    private ImageRepository imageRepository ;
    private ServletContext context ;

    public ArticleServiceImpl(ArticleRepository articleRepository, ImageRepository imageRepository, ServletContext context) {
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
        this.context = context;
    }

    @Override
    public List<Article> getAllArticles() {
        return this.articleRepository.findAll();
    }

    @Override
    public Article getArticleByTitle(String title){
        if( !this.articleRepository.findArticleByTitle(title).isPresent()){
             throw new ApiRequestException(" article not found ") ;
        }
        return this.articleRepository.findArticleByTitle(title).get() ;
    }

    @Override
    public Page<Article> getArticleByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.articleRepository.findAll(pageable);
    }

    @Override
    public Page<ArticleProjection> getArticleByProjectionAndPages(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.articleRepository.getArticlesByPages(pageable);
    }

    @Override
    public Page<ArticleProjection> getArticleByProjectionAndPagesAndByCategory(String categoryName,int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.articleRepository.findArticlesByCategory_CategoryName(categoryName , pageable);
    }


    @Override
    public Article getArticleByIdArticle(Long idaArticle) {
        Article article = this.articleRepository.findById(idaArticle).get() ;
        if(article == null ){
            throw new ApiRequestException(" this article doesn't exists ");
        }
        return article ;
    }

    @Override
    public Page<ArticleProjection> getArticlesByWordAndPageAndProjection(String word, int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.articleRepository.getArticlesByWord(word , pageable);
    }

    @Override
    public Article addNewArticle(MultipartFile[] files, String article) {
        Article newArticle = this.createArticle(files , article ) ;
        return this.articleRepository.save(newArticle);
    }

    @Override
    public Article updateArticle(String article) {
        Article updatedArticle = this.updateArticleFunc(article) ;
        return this.articleRepository.save(updatedArticle) ;
    }

    @Override
    public void deleteArticle(Long idArticle) {
        if(!this.articleRepository.findById(idArticle).isPresent()){
            throw new ApiRequestException(" article doesn't exists ") ;
        }
        this.deleteDirectory(idArticle);
        this.articleRepository.deleteById(idArticle); ;
    }

    private Article createArticle(MultipartFile[] files ,String article ){

        Article _article = null ;
        String[] imagesUrl = new String[5] ;
        List<Image> images = new ArrayList<>() ;

        try {
            _article = new ObjectMapper().readValue(article , Article.class);
        } catch (JsonProcessingException e) {
            throw new ApiRequestException(e.getMessage()) ;
        }

        if(this.articleRepository.findArticleByTitle(_article.getTitle()).isPresent()){
            throw new ApiRequestException(" this article title already exists ") ;
        }


        String url = ( ImageUtil.ARTICLES + _article.getCategory().getCategoryName() + "/" + _article.getTitle() + "/" ).replaceAll("\\s+" , "") ;
        boolean isExist = new File(context.getRealPath(url)).exists();

        if (!isExist) {
            new File(context.getRealPath(url)).mkdir();
        }

        for(int i= 0 ; i < files.length ; i++ ) {

            String filename = files[i].getOriginalFilename();
            String newFileName = ( _article.getCategory().getCategoryName() + "/" + _article.getTitle() ).replaceAll("\\s+" , "" ) + "/" + FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename) ;
            imagesUrl[i] = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath(ImageUtil.ARTICLES + File.separator + newFileName));

            try {
                FileUtils.writeByteArrayToFile(serverFile, files[i].getBytes());
            } catch (Exception e) {
                throw new ApiRequestException(e.getMessage()) ;
            }

        }

        Image image = new Image(null,imagesUrl[0]);
        Image image2 = new Image(null,imagesUrl[1]);

        this.imageRepository.save(image) ;
        this.imageRepository.save(image2) ;


        images.add(image);
        images.add(image2);

        _article.setImages(images);

        return _article ;
    }

    private Article updateArticleFunc(String article ){

        Article _article  ;
        Article oldestArticle  ;
        String[] imagesUrl = new String[5] ;
        List<Image> images = new ArrayList<>() ;

        try {
            _article = new ObjectMapper().readValue(article , Article.class);
        } catch (JsonProcessingException e) {
            throw new ApiRequestException(e.getMessage()) ;
        }

        if(!this.articleRepository.findById(_article.getIdArticle()).isPresent()){
            throw new ApiRequestException(" article ot Found ") ;
        }

        oldestArticle = this.articleRepository.findById(_article.getIdArticle()).get() ;

        if(! ( oldestArticle.getTitle().equals(_article.getTitle()) && oldestArticle.getCategory().getCategoryName().equals(_article.getCategory().getCategoryName()) ) ){

            String url = ( ImageUtil.ARTICLES + oldestArticle.getCategory().getCategoryName() + "/" + oldestArticle.getTitle() + "/" ).replaceAll("\\s+" , "") ;
            String urlUpdated = ( ImageUtil.ARTICLES + _article.getCategory().getCategoryName() + "/" + _article.getTitle() + "/" ).replaceAll("\\s+" , "") ;
            boolean isExist = new File(context.getRealPath(url)).exists();

            if (isExist) {
                Path source = Paths.get(context.getRealPath( url ));
                Path newSource = Paths.get(context.getRealPath( urlUpdated ));
                try {
                    Files.move( source , newSource );
                } catch (IOException e) {
                    throw new ApiRequestException(e.getMessage()) ;
                }
            }else{
                new File(context.getRealPath( urlUpdated )).mkdir() ;
            }

        }

        _article.setImages( oldestArticle.getImages() ) ;
        _article.setComments(oldestArticle.getComments() ) ;

        return _article ;
    }

    private void deleteDirectory(Long idArticle){
        Article article = this.articleRepository.findById(idArticle).get() ;
        String url = ( ImageUtil.ARTICLES + article.getCategory().getCategoryName() + "/" + article.getTitle() ).replaceAll("\\s+" , "") ;
        boolean isExist = new File(context.getRealPath(url)).exists() ;
        if (isExist) {

            File[] files =  new File(context.getRealPath(url)).listFiles() ;
            for (File file : files){
                try {
                    file.delete() ;
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            try {
                new File(context.getRealPath(url)).delete() ;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

}
