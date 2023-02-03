package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.model.projection.ProjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects() ;

    Page<Project> getProjectByPage(int offset , int size) ;

    Page<ProjectProjection> getProjectByPageAndProjection(int offset , int size) ;

    Page<ProjectProjection> getProjectByWord(String word , int offset , int size) ;

    Project getProjectByIdProject(Long idProject) ;

    Project getProjectByName(String projectName) ;

    Project addNewProject(MultipartFile[] files, String project) ;

    Project updateProject(String project) ;

    void deleteProject(Long idProject) ;

}
