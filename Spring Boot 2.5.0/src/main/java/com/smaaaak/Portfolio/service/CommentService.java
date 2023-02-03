package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments() ;

    Page<Comment> getCommentByPage(int offset , int size) ;

    Long getCommentsCount() ;

    Comment addNewComment(Comment newComment) ;

    void deleteComment(Long idComment) ;

}
