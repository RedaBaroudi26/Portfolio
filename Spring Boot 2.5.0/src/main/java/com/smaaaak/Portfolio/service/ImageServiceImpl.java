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
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository ;
    private final ArticleRepository articleRepository ;
    private final ProjectRepository projectRepository ;
    private final ServletContext context ;
    private final SkillRepository skillRepository ;


    @Override
    public byte[] getImage( Long idImage , Long idObject  , String className ) throws Exception{

        Image image = this.imageRepository.findById(idImage)
                .orElseThrow( () -> new ApiRequestException(" image doesn't exists ") ) ;

        Article article  ;
        Project project  ;
        String imageUrl = "" ;


        if(className.equals("Article")){

            article = this.articleRepository.findById(idObject)
                    .orElseThrow( () -> new ApiRequestException(" article not found ") ) ;

            imageUrl = ( ImageUtil.ARTICLES + article.getCategory().getCategoryName() + "/" + article.getTitle() + "/" )
                             .replaceAll("\\s+" , "") ;

        }else if(className.equals("Project")){

            project = this.projectRepository.findById(idObject)
                          .orElseThrow( () ->  new ApiRequestException(" Project not found ")  ) ;
            imageUrl = ( ImageUtil.PROJECTS + project.getProjectName() + "/" ).replaceAll("\\s+" , "") ;

        } else if (className.equals("Skill")) {

            this.skillRepository.findById(idObject).orElseThrow( () -> new ApiRequestException(" Skill not found ")) ;
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

        Article article = null ;
        Project project = null ;
        Skill skill = null ;
        String url = "" ;
        String imageUrl2 ;
        boolean isExists = false ;

        Image image = this.imageRepository.findById(idImage).orElseThrow(
                ()-> new ApiRequestException(" image doesn't exists ")
        );

        if(className.equals("Article")){

            article = this.articleRepository.findById(idObject).orElseThrow(
                    () -> new ApiRequestException(" article not found ")
            ) ;

            url = ( ImageUtil.ARTICLES + article.getCategory().getCategoryName() + "/" + article.getTitle() + "/" )
                     .replaceAll("\\s+" ,"") ;

        }else if(className.equals("Project")){

            project = this.projectRepository.findById(idObject).orElseThrow(
                    () ->  new ApiRequestException(" Project not found ")
            );

            url = ( ImageUtil.PROJECTS + project.getProjectName() + "/" ).replaceAll("\\s+" , "" ) ;

        }else if(className.equals("Skill")){

            skill = this.skillRepository.findById(idObject).orElseThrow(
                    () -> new ApiRequestException(" Skill not found ")
            ) ;

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
