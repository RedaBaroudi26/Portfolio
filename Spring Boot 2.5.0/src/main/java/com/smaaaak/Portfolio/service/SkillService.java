package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SkillService {

     List<Skill> allSkills() ;

     Skill findSkillById(Long idSkill) ;

     Page<Skill> getSkillsByPage(int offset , int size) ;
     void addSkill(MultipartFile file , String newSkill) ;
     void updateSkill(Skill updatedSkill ) ;
     void deleteSkill (Long idSkill) ;

}
