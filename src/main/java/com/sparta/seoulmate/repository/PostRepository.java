package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User user);

    Page<Post> findAllByAuthor(User Author, Pageable pageable);
}
