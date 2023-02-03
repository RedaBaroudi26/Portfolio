package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Article;
import com.smaaaak.Portfolio.model.Image;
import com.smaaaak.Portfolio.model.Project;
import com.smaaaak.Portfolio.model.Skill;
import com.smaaaak.Portfolio.repository.ArticleRepository;
import com.smaaaak.Portfolio.repository.ImageRepository;
import com.smaaaak.Portfolio.repository.ProjectRepository;
import com.smaaaak.Portfolio.repository.SkillRepository;
import com.smaaaak.Portfolio.share.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository ;
    private ArticleRepository articleRepository ;
    private ProjectRepository projectRepository ;
    private ServletContext context ;

    private SkillRepository skillRepository ;

    public ImageServiceImpl(ImageRepository imageRepository, ArticleRepository articleRepository, ProjectRepository projectRepository, ServletContext context, SkillRepository skillRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
        this.projectRepository = projectRepository;
        this.context = context;
        this.skillRepository = skillRepository;
    }

    @Override
    public byte[] getImage( Long idImage , Long idObject  , String className ) throws Exception{

        Image image = this.imageRepository.findById(idImage).get() ;
        Article article  ;
        Project project  ;
        Skill skill  ;
        String imageUrl = "" ;

        if(image == null ){
            throw new ApiRequestException(" image doesn't exists ") ;
        }

        if(className.equals("Article")){
            if(!this.articleRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" article not found ") ;
            }
            article = this.articleRepository.findById(idObject).get() ;
            imageUrl =  ( ImageUtil.ARTICLES + article.getCategory().getCategoryName() + "/" + article.getTitle() + "/" ).replaceAll("\\s+" , "") ;
        }else if(className.equals("Project")){
            if(!this.projectRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" Project not found ") ;
            }
            project = this.projectRepository.findById(idObject).get() ;
            imageUrl = ( ImageUtil.PROJECTS + project.getProjectName() + "/" ).replaceAll("\\s+" , "") ;
        } else if (className.equals("Skill")) {
            if(!this.skillRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" Skill not found ") ;
            }
           // skill = this.skillRepository.findById(idObject).get() ;
            imageUrl = ImageUtil.SKILLS  ;
        }

        boolean isExist = new File(context.getRealPath( imageUrl + image.getImageUrl() ) ).exists() ;
        if(!isExist){
            throw new ApiRequestException("File not Found");
        }

        return Files.readAllBytes(Paths.get(context.getRealPath( imageUrl + image.getImageUrl() ))) ;
    }

    @Override
    public void updateImage(Long idImage , Long idObject , String className , MultipartFile file) {

        Image image = this.imageRepository.findById(idImage).get() ;
        Article article = null ;
        Project project = null ;
        Skill skill = null ;
        String url = "" ;
        String imageUrl2 ;
        boolean isExists = false ;

        if(image == null ){
            throw new ApiRequestException(" image doesn't exists ") ;
        }

        if(className.equals("Article")){

            if(!this.articleRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" article not found ") ;
            }
            article = this.articleRepository.findById(idObject).get() ;
            url = ( ImageUtil.ARTICLES + article.getCategory().getCategoryName() + "/" + article.getTitle() + "/" ).replaceAll("\\s+" ,"") ;

        }else if(className.equals("Project")){

            if(!this.projectRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" Project not found ") ;
            }

            project = this.projectRepository.findById(idObject).get() ;
            url = ( ImageUtil.PROJECTS + project.getProjectName() + "/" ).replaceAll("\\s+" , "" ) ;

        }else if(className.equals("Skill")){

            if(!this.skillRepository.findById(idObject).isPresent()){
                throw new ApiRequestException(" Skill not found ") ;
            }

            skill = this.skillRepository.findById(idObject).get() ;
            url = ImageUtil.SKILLS  ;

        }


        isExists = new File( context.getRealPath( url + image.getImageUrl() ).replaceAll("\\s+" ,"") ).exists() ;

        if(isExists) {
            try {
                new File(context.getRealPath(url + image.getImageUrl() )).delete();
            } catch (Exception e) {
                throw new ApiRequestException(e.getMessage());
            }
        }

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename) ;
        imageUrl2 = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath(url + File.separator + newFileName));

        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage()) ;
        }

        Image newImage = new Image(null , imageUrl2) ;
        this.imageRepository.save(newImage) ;

        if(className.equals("Article")){

            List<Image> images = article.getImages() ;
            images.remove(image) ;
            images.add(newImage) ;
            article.setImages( images );
            this.articleRepository.save(article) ;

        }else if(className.equals("Project")){

            List<Image> images = project.getImages() ;
            images.remove(image) ;
            images.add(newImage) ;
            project.setImages( images );
            this.projectRepository.save(project) ;

        }else if(className.equals("Skill")){

            skill.setImage(newImage);
            this.skillRepository.save(skill) ;

        }

        this.imageRepository.deleteById(idImage);

    }

}
