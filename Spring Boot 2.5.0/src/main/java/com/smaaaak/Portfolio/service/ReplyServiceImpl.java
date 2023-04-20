package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.model.Reply;
import com.smaaaak.Portfolio.repository.CommentRepository;
import com.smaaaak.Portfolio.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository ;
    private final CommentRepository commentRepository ;


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
        this.commentRepository.findById(newReply.getComment().getIdComment()).orElseThrow(
                () -> new ApiRequestException(" comment doesn't exists ")
        );
        return this.replyRepository.save(newReply);
    }

    @Override
    public void deleteReply(Long idReply) {
        this.replyRepository.deleteById(idReply);
    }

}
