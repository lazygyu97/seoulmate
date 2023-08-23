package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
