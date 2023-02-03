package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Tag;
import com.smaaaak.Portfolio.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository ;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return this.tagRepository.findAll();
    }

    @Override
    public Long getTagsCount() {
        return this.tagRepository.count();
    }

    @Override
    public Page<Tag> getTagByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.tagRepository.findAll(pageable);
    }

    @Override
    public Tag addNewTag(Tag newTag) {
        if( this.tagRepository.findTagByTagName(newTag.getTagName()).isPresent() ){
            throw new ApiRequestException(" tag already exists ") ;
        }
        return this.tagRepository.save(newTag) ;
    }

    @Override
    public Tag updateTag(Tag tag) {
        if( !this.tagRepository.findById(tag.getIdTag()).isPresent() ){
           throw new ApiRequestException(" tag doesn't exists ") ;
        }
        if( this.tagRepository.findTagByTagName(tag.getTagName()).isPresent() ){
            throw new ApiRequestException(" tag already exists ") ;
        }
        return this.tagRepository.save(tag) ;
    }

    @Override
    public void deleteTag(Long idTag) {
        if(!this.tagRepository.findById(idTag).isPresent()){
            throw new ApiRequestException(" tag doesn't exists ") ;
        }
        this.tagRepository.deleteById(idTag);
    }

}
