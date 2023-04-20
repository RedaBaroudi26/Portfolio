package com.smaaaak.Portfolio.dto;


import com.smaaaak.Portfolio.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

   private String ProjectName ;
   private String description ;
   private String completedDate ;
   private boolean completed ;
   private String clientName ;
   private String rating ;
   private String webSiteUrl ;
   private List<Image> images ;


}
