package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Reply;
import com.smaaaak.Portfolio.service.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/replies")
@AllArgsConstructor
public class ReplyController {

    private final ReplyService replyService ;

    @GetMapping
    public ResponseEntity<List<Reply>> getAllReplies(){
        return new ResponseEntity<>(this.replyService.getAllReplies() , HttpStatus.OK) ;
    }

    @GetMapping("/byPages/{offset}/{size}")
    public ResponseEntity<Page<Reply>> getRepliesByPagination(@PathVariable("offset") int offset , @PathVariable("size") int size){
        return new ResponseEntity<>(this.replyService.getReplyByPage(offset, size) , HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<Reply> createReply(@RequestBody Reply newReply){
        return new ResponseEntity<>(this.replyService.addNewReply(newReply) , HttpStatus.CREATED) ;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable("id") Long idReply){
        this.replyService.deleteReply(idReply); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }
}
