package com.smaaaak.Portfolio.dto;

import com.smaaaak.Portfolio.model.Comment;
import com.smaaaak.Portfolio.model.Image;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDto {


    private String title ;
    private String description ;
    private String writerName ;
    private Date dateCreation ;
    private String categoryName ;
    private List<Image> images ;
    private List<Comment> comments ;


}
