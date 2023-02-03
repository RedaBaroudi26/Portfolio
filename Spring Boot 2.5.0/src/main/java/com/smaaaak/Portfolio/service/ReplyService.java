package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.Reply;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReplyService {

    List<Reply> getAllReplies() ;

    Page<Reply> getReplyByPage(int offset , int size) ;

    Reply addNewReply(Reply newReply) ;

    void deleteReply(Long idReply) ;

}
