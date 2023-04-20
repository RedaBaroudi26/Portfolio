package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Tag;
import com.smaaaak.Portfolio.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository ;


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
        this.tagRepository.findTagByTagName(newTag.getTagName()).ifPresent(
                (tag) -> { throw new ApiRequestException(" tag already exists ") ; }
        );
        return this.tagRepository.save(newTag) ;
    }

    @Override
    public Tag updateTag(Tag tag) {
        this.tagRepository.findById(tag.getIdTag()).orElseThrow(
                () -> new ApiRequestException(" tag doesn't exists ")
        );
        this.tagRepository.findTagByTagName(tag.getTagName()).ifPresent(
                (t) -> { throw new ApiRequestException(" tag already exists ") ; }
        );
        return this.tagRepository.save(tag) ;
    }

    @Override
    public void deleteTag(Long idTag) {
        this.tagRepository.findById(idTag).orElseThrow(
                () -> new ApiRequestException(" tag doesn't exists ")
        );
        this.tagRepository.deleteById(idTag);
    }

}
