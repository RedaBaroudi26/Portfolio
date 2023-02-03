package com.smaaaak.Portfolio.model.projection;


import com.smaaaak.Portfolio.model.Category;
import com.smaaaak.Portfolio.model.Comment;

import java.util.Date;
import java.util.List;

public interface ArticleProjection {

    Long getIdArticle() ;

    String getTitle();

    String getWriterName() ;

    Date getDateCreation() ;

    String getDescription();

    Category getCategory() ;

    List<ImageProjection> getImages();

    List<Comment> getComments() ;

}
