package com.smaaaak.Portfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Image;
import com.smaaaak.Portfolio.model.Skill;
import com.smaaaak.Portfolio.repository.ImageRepository;
import com.smaaaak.Portfolio.repository.SkillRepository;
import com.smaaaak.Portfolio.share.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;

import java.util.List;


@Service
public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository ;
    private ServletContext context ;
    private ImageRepository imageRepository ;


    public SkillServiceImpl(SkillRepository skillRepository, ServletContext context, ImageRepository imageRepository) {
        this.skillRepository = skillRepository;
        this.context = context;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Skill> allSkills() {
        return this.skillRepository.findAll();
    }

    @Override
    public Skill findSkillById(Long idSkill) {
        if (this.skillRepository.findById(idSkill).isEmpty()){
            throw new ApiRequestException("Skill Not Found") ;
        }
        return this.skillRepository.findById(idSkill).get();
    }

    @Override
    public Page<Skill> getSkillsByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.skillRepository.findAll(pageable);
    }

    @Override
    public void addSkill(MultipartFile file , String newSkill) {
       Skill skill = createSkill(file , newSkill) ;
       this.skillRepository.save(skill) ;
    }

    @Override
    public void updateSkill(Skill updatedSkill) {
        if( this.skillRepository.findById(updatedSkill.getIdSkill()).isEmpty() ){
            throw new ApiRequestException(" this skill not found ") ;
        }
        updatedSkill.setImage(this.skillRepository.findById(updatedSkill.getIdSkill()).get().getImage());
        this.skillRepository.save(updatedSkill) ;
    }

    @Override
    public void deleteSkill(Long idSkill) {
        if( this.skillRepository.findById(idSkill).isEmpty() ){
            throw new ApiRequestException(" this skill not found ") ;
        }
        this.deleteSkillImage(idSkill);
        this.skillRepository.deleteById(idSkill);
    }

    private Skill createSkill(MultipartFile file , String newSkill){

        Skill _skill = null ;
        String imageUrl  ;

        try {
            _skill = new ObjectMapper().readValue(newSkill , Skill.class);
        } catch (JsonProcessingException e) {
            throw  new ApiRequestException(e.getMessage()) ;
        }

        if( this.skillRepository.findSkillBySkillName(_skill.getSkillName()).isPresent() ){
            throw new ApiRequestException(" this skill already exist") ;
        }

        String filename = file.getOriginalFilename();
        imageUrl = ( FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename) ).replaceAll("\\s+" , "" ) ;
        File serverFile = new File(context.getRealPath(ImageUtil.SKILLS + File.separator + imageUrl));

        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (Exception e) {
            new ApiRequestException(e.getMessage()) ;
        }

        Image image = new Image(null,imageUrl);

        this.imageRepository.save(image) ;

        _skill.setImage(image);

        return _skill ;
    }

    private void deleteSkillImage(Long idSkill){
        Skill skill = this.skillRepository.findById(idSkill).get() ;
        String url = context.getRealPath(ImageUtil.SKILLS + skill.getImage().getImageUrl()) ;
        boolean isExist = new File(url).exists();
        if (isExist) {
            new File(url).delete() ;
        }
    }


}
