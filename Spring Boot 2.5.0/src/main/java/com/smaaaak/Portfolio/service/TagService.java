package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags() ;

    Page<Tag> getTagByPage(int offset , int size) ;

    Long getTagsCount() ;

    Tag addNewTag(Tag newTag) ;

    Tag updateTag(Tag tag) ;

    void deleteTag(Long idTag) ;

}
