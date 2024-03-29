package com.smaaaak.Portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.dto.ProjectDto;
import com.smaaaak.Portfolio.mapper.MapStructMapper;
import com.smaaaak.Portfolio.model.Image;
import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.repository.ImageRepository;
import com.smaaaak.Portfolio.repository.ProjectRepository;
import com.smaaaak.Portfolio.share.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository ;
    private final ImageRepository imageRepository ;
    private final ServletContext context ;
    private final MapStructMapper mapper ;

    @Override
    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public Page<Project> getProjectByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.projectRepository.findAll(pageable);
    }

    @Override
    public Page<ProjectDto> getProjectByPageAndProjection(int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.projectRepository.getProjectsByPages(pageable).map(mapper::projectToProjectDto);
    }

    @Override
    public Project getProjectByIdProject(Long idProject) {
        return this.projectRepository.findById(idProject)
                .orElseThrow(() ->new ApiRequestException(" this project doesn't exists ") );
    }

    @Override
    public Project getProjectByName(String projectName) {
        return this.projectRepository.findProjectByProjectName(projectName).orElseThrow(
                () -> new ApiRequestException(" this project doesn't exists ")
        ) ;
    }

    @Override
    public Page<ProjectDto> getProjectByWord(String word, int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.projectRepository.getProjectsByWord(word , pageable).map(mapper::projectToProjectDto);
    }

    @Override
    public Project addNewProject(MultipartFile[] files, String project) {
        Project newProject = this.createProject(files , project) ;
        return this.projectRepository.save(newProject) ;
    }

    @Override
    public Project updateProject(String project) {
        Project updatedProject = this.updateProjectFunc(project) ;
        return this.projectRepository.save(updatedProject);
    }

    @Override
    public void deleteProject(Long idProject) {
        getProjectByIdProject(idProject) ;
        this.deleteDirectory(idProject) ;
        this.projectRepository.deleteById(idProject) ;
    }

    private Project createProject(MultipartFile[] files , String project){

        Project _project = null ;
        String[] imagesUrl = new String[5] ;
        List<Image> images = new ArrayList<>() ;

        boolean isExist = new File(context.getRealPath(ImageUtil.PROJECTS)).exists();
        if (!isExist) {
            new File(context.getRealPath(ImageUtil.PROJECTS)).mkdir();
        }

        try {
            _project = new ObjectMapper().readValue(project , Project.class);
        } catch (JsonProcessingException e) {
            throw  new ApiRequestException(e.getMessage()) ;
        }

      /*  if(this.projectRepository.findProjectByProjectName(_project.getProjectName()).isPresent()){
            throw new ApiRequestException(" this project Name already exist ") ;
        } */

        this.projectRepository.findProjectByProjectName(_project.getProjectName()).ifPresent(
                (project1) -> { throw new ApiRequestException(" this project Name already exist ") ; }
        );

        for( int i= 0 ; i < files.length ; i++ ) {

            String filename = files[i].getOriginalFilename();
            String newFileName = _project.getProjectName() + "/" + FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
            imagesUrl[i] = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath(ImageUtil.PROJECTS + File.separator + newFileName));

            try {
                FileUtils.writeByteArrayToFile(serverFile, files[i].getBytes());
            } catch (Exception e) {
                new ApiRequestException(e.getMessage()) ;
            }

        }

        Image image = new Image(null,imagesUrl[0]);
        Image image2 = new Image(null,imagesUrl[1]);

        this.imageRepository.save(image) ;
        this.imageRepository.save(image2) ;

        images.add(image);
        images.add(image2);

        _project.setImages(images);

        return _project ;
    }

    private Project updateProjectFunc(String project){

        Project _project = null ;
        String[] imagesUrl = new String[5] ;
        List<Image> images = new ArrayList<>() ;

        try {
            _project = new ObjectMapper().readValue(project , Project.class);
        } catch (JsonProcessingException e) {
            throw  new ApiRequestException(e.getMessage()) ;
        }


        this.projectRepository.findById(_project.getIdProject()).orElseThrow(
                () -> new ApiRequestException(" project doesn't exists ")
        ) ;

        Project oldestProject = this.projectRepository.findById(_project.getIdProject()).get() ;

        if( !oldestProject.getProjectName().equals(_project.getProjectName())  ){

            String url = ( ImageUtil.PROJECTS + oldestProject.getProjectName() + "/" ).replaceAll("\\s+" , "") ;
            String urlUpdated = ( ImageUtil.ARTICLES + _project.getProjectName() + "/").replaceAll("\\s+" , "") ;

            boolean isExist = new File(context.getRealPath(url)).exists();

            if (isExist) {
                Path source = Paths.get(context.getRealPath( url ));
                Path newSource = Paths.get(context.getRealPath( urlUpdated ));
                try {
                    Files.move( source , newSource );
                } catch (IOException e) {
                    throw new ApiRequestException(e.getMessage()) ;
                }
            }else{
                new File(context.getRealPath( urlUpdated )).mkdir() ;
            }

        }

        _project.setImages( oldestProject.getImages() ) ;

        return _project ;
    }

    private void deleteDirectory(Long idProject){

        Project project = getProjectByIdProject(idProject) ;
        String url = ( ImageUtil.PROJECTS + project.getProjectName() + "/" ).replaceAll("\\s+" , "") ;
        boolean isExist = new File(context.getRealPath(url)).exists() ;

        if (isExist) {

            File[] files =  new File(context.getRealPath(url)).listFiles() ;

            for (File file : files){
                try {
                    file.delete() ;
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            try {
                new File(context.getRealPath(url)).delete() ;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

    }


}
