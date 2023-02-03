package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Comment;
import com.smaaaak.Portfolio.repository.ArticleRepository;
import com.smaaaak.Portfolio.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository ;
    private ArticleRepository articleRepository ;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    @Override
    public Page<Comment> getCommentByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset , size) ;
        return this.commentRepository.findAll(pageable);
    }

    @Override
    public Long getCommentsCount() {
        return this.commentRepository.count() ;
    }


    @Override
    public Comment addNewComment(Comment newComment) {
        if(!this.articleRepository.findById(newComment.getArticle().getIdArticle()).isPresent()){
            throw new ApiRequestException(" article doesn't exists ") ;
        }
        return this.commentRepository.save(newComment);
    }

    @Override
    public void deleteComment(Long idComment) {
        if(!this.commentRepository.findById(idComment).isPresent()){
            throw  new ApiRequestException(" comment doesn't exists ") ;
        }
        this.commentRepository.deleteById(idComment);
    }

}
