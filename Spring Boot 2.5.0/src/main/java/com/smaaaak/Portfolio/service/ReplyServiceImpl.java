package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Reply;
import com.smaaaak.Portfolio.repository.CommentRepository;
import com.smaaaak.Portfolio.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private ReplyRepository replyRepository ;
    private CommentRepository commentRepository ;

    public ReplyServiceImpl(ReplyRepository replyRepository, CommentRepository commentRepository) {
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Reply> getAllReplies() {
        return this.replyRepository.findAll();
    }

    @Override
    public Page<Reply> getReplyByPage(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size) ;
        return this.replyRepository.findAll(pageable) ;
    }

    @Override
    public Reply addNewReply(Reply newReply) {
        if(!this.commentRepository.findById(newReply.getComment().getIdComment()).isPresent()){
           throw new ApiRequestException(" comment doesn't exists ") ;
        }
        return this.replyRepository.save(newReply);
    }

    @Override
    public void deleteReply(Long idReply) {
        this.replyRepository.deleteById(idReply);
    }

}
