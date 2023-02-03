package com.smaaaak.Portfolio.model.projection;

import java.util.List;

public interface ProjectProjection {

    String getIdProject() ;

    String getProjectName();

    String getCompletedDate() ;

    Boolean getCompleted() ;

    String getClientName() ;

    String getRating() ;

    String getDescription();

    String getWebSiteUrl() ;

    List<ImageProjection> getImages();

}
