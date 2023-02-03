package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.model.projection.ProjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p")
    Page<ProjectProjection> getProjectsByPages(Pageable pageable) ;

    @Query("select p from Project p where p.description like CONCAT('%', :word , '%') or p.content like CONCAT('%', :word , '%') or p.projectName like CONCAT('%', :word , '%')  ")
    Page<ProjectProjection> getProjectsByWord(String word , Pageable pageable) ;

    Optional<Project> findProjectByProjectName(String projectName) ;

}