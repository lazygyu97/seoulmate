package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
