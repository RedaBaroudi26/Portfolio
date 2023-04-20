package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Comment;
import com.smaaaak.Portfolio.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService ;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(this.commentService.getAllComments() , HttpStatus.OK) ;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCommentsCount(){
        return new ResponseEntity<>(this.commentService.getCommentsCount() , HttpStatus.OK) ;
    }

    @GetMapping("byPages/{offset}/{size}")
    public ResponseEntity<Page<Comment>> getCommentsByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.commentService.getCommentByPage(offset, size) , HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment newComment){
        return new ResponseEntity<>(this.commentService.addNewComment(newComment) , HttpStatus.CREATED) ;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long idComment){
        this.commentService.deleteComment(idComment); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
