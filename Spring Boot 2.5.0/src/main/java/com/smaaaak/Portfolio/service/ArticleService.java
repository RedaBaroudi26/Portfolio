package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.dto.ArticleDto;
import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.projection.ArticleProjection;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles() ;

    Article getArticleByTitle(String title) ;

    Page<ArticleDto> getArticleByProjectionAndPagesAndByCategory(String categoryName , int offset, int size) ;

    Page<Article> getArticleByPage(int offset , int size) ;

    Page<ArticleDto> getArticleByProjectionAndPages(int offset , int size) ;

    Article getArticleByIdArticle(Long idaArticle) ;

    Page<ArticleDto> getArticlesByWordAndPageAndProjection(String word, int offset , int size) ;

    Article addNewArticle(MultipartFile[] files, String article) ;

    Article updateArticle( String article) ;

    void deleteArticle(Long idArticle) ;


}
