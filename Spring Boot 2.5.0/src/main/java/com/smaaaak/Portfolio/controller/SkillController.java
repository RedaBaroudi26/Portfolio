package com.smaaaak.Portfolio.controller;


import com.smaaaak.Portfolio.model.Skill;
import com.smaaaak.Portfolio.service.ImageService;
import com.smaaaak.Portfolio.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/skill")
public class SkillController {

    private SkillService skillService ;
    private ImageService imageService ;

    public SkillController(SkillService skillService, ImageService imageService) {
        this.skillService = skillService;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills(){
        return new ResponseEntity<>(this.skillService.allSkills() , HttpStatus.OK) ;
    }

    @GetMapping("/skillById/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") Long idSkill ){
        return new ResponseEntity<>(this.skillService.findSkillById(idSkill) , HttpStatus.OK) ;
    }

    @GetMapping("/skillByPage/{offset}/{size}")
    public ResponseEntity<Page<Skill>> getSkillsByPage(@PathVariable("offset") int offset , @PathVariable("size") int size ){
        return new ResponseEntity<>(this.skillService.getSkillsByPage(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/imageskill/{idSkill}/{idImage}")
    public byte[] getPhoto(@PathVariable("idImage") Long idImage ,@PathVariable("idSkill") Long idSkill ) throws Exception{
        return  this.imageService.getImage(idImage , idSkill , "Skill") ;
    }

    @PutMapping("/updateImage")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file , @RequestParam("idSkill") Long idSkill , @RequestParam("idImage") Long idImage){
        this.imageService.updateImage(idImage , idSkill , "Skill" , file) ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSkill(@RequestParam("file") MultipartFile file , @RequestParam("skill") String skill ){
        this.skillService.addSkill(file , skill );
        return new ResponseEntity<>(HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateArticle(@RequestBody Skill skill){
        this.skillService.updateSkill(skill);
        return new ResponseEntity<>( HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long idSkill){
        this.skillService.deleteSkill(idSkill); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
