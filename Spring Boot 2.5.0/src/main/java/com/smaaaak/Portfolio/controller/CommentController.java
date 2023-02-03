package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Comment;
import com.smaaaak.Portfolio.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    private CommentService commentService ;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(this.commentService.getAllComments() , HttpStatus.OK) ;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCommentsCount(){
        return new ResponseEntity<>(this.commentService.getCommentsCount() , HttpStatus.OK) ;
    }

    @GetMapping("/commentsByPages/{offset}/{size}")
    public ResponseEntity<Page<Comment>> getCommentsByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.commentService.getCommentByPage(offset, size) , HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Comment> createComment(@RequestBody Comment newComment){
        return new ResponseEntity<>(this.commentService.addNewComment(newComment) , HttpStatus.CREATED) ;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long idComment){
        this.commentService.deleteComment(idComment); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
