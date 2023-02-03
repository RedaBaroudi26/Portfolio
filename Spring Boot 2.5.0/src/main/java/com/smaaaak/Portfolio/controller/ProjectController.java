package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.Image;
import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.model.projection.ProjectProjection;
import com.smaaaak.Portfolio.service.ImageService;
import com.smaaaak.Portfolio.service.ProjectService;
import com.smaaaak.Portfolio.share.ImageUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    private ProjectService projectService ;
    private ImageService imageService ;

    public ProjectController(ProjectService projectService, ImageService imageService) {
        this.projectService = projectService;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects(){
        return new ResponseEntity<>(this.projectService.getAllProjects() , HttpStatus.OK) ;
    }

    @GetMapping("/projectsByPages/{offset}/{size}")
    public ResponseEntity<Page<Project>> getProjectsByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.projectService.getProjectByPage(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/projectsByPagesAndProjection/{offset}/{size}")
    public ResponseEntity<Page<ProjectProjection>> getProjectsByPaginationAndProjection(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.projectService.getProjectByPageAndProjection(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/projectsByWordAndProjection/{word}/{offset}/{size}")
    public ResponseEntity<Page<ProjectProjection>> getProjectsByWordAndPaginationAndProjection( @PathVariable("word") String word ,@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.projectService.getProjectByWord(word ,offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/projectById/{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") Long idProject ){
        return new ResponseEntity<>(this.projectService.getProjectByIdProject(idProject) , HttpStatus.OK) ;
    }

    @GetMapping("/projectByName/{projectName}")
    public ResponseEntity<Project> getProject(@PathVariable("projectName") String projectName){
        return new ResponseEntity<>(this.projectService.getProjectByName(projectName) , HttpStatus.OK) ;
    }

    @GetMapping("/imageProject/{idImage}/{idProject}")
    public byte[] getPhoto(@PathVariable("idImage") Long idImage , @PathVariable("idProject") Long idProject ) throws Exception{
        return  this.imageService.getImage(idImage , idProject , "Project" ) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Project> createProject(@RequestParam("files") MultipartFile[] files , @RequestParam("project") String project ){
        return new ResponseEntity<>(this.projectService.addNewProject(files , project ) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestParam("project") String project){
        return new ResponseEntity<>(this.projectService.updateProject( project) , HttpStatus.OK) ;
    }

    @PutMapping("/updateImage")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file , @RequestParam("idProject") Long idProject , @RequestParam("idImage") Long idImage){
        this.imageService.updateImage(idImage , idProject , "Project" , file) ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long idProject){
        this.projectService.deleteProject(idProject); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
