package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}