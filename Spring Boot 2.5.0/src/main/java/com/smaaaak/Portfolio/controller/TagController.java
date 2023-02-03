package com.smaaaak.Portfolio.controller;


import com.smaaaak.Portfolio.model.Tag;
import com.smaaaak.Portfolio.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tag")
public class TagController {

    private TagService tagService ;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags(){
        return new ResponseEntity<>( this.tagService.getAllTags() , HttpStatus.OK) ;
    }

    @GetMapping("/TagsByPages/{offset}/{size}")
    public ResponseEntity<Page<Tag>> getTagsByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>( this.tagService.getTagByPage(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTagsCount(){
        return new ResponseEntity<>( this.tagService.getTagsCount() , HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Tag> createTag(@RequestBody Tag newTag){
        return new ResponseEntity<>( this.tagService.addNewTag(newTag) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag){
        return new ResponseEntity<>( this.tagService.updateTag(tag) , HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") Long idTag){
        this.tagService.deleteTag(idTag) ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
