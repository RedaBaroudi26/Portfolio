package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsArticleByCategory_CategoryName(String categoryName) ;

    @Query("select a from Article a")
    Page<Article> getArticlesByPages(Pageable pageable) ;

    @Query("select a from Article a where a.description like CONCAT('%', :word , '%') or a.content like CONCAT('%', :word , '%') or a.title like CONCAT('%', :word , '%')  ")
    Page<Article> getArticlesByWord(String word , Pageable pageable) ;

    Optional<Article> findArticleByTitle(String title) ;

    Page<Article> findArticlesByCategory_CategoryName(String categoryName , Pageable pageable) ;


}