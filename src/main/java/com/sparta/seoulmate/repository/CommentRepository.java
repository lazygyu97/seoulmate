package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();
    // Comment 엔티티의 모든 레코드를 modifiedAt 속성을 기준으로 내림차순으로 정렬하여 검색하는 기능
}
