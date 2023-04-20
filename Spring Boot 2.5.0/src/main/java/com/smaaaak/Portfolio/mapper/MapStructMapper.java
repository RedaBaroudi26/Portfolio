package com.smaaaak.Portfolio.mapper;


import com.smaaaak.Portfolio.dto.ArticleDto;
import com.smaaaak.Portfolio.dto.ProfileDto;
import com.smaaaak.Portfolio.dto.ProjectDto;
import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

     @Mapping( source = "article.category.categoryName" , target = "categoryName" )
     ArticleDto articleToArticleDTO(Article article) ;


     ProfileDto userToProfileDTO(User user) ;

     ProjectDto projectToProjectDto(Project project) ;


}
