package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.PostLike;
import com.sparta.seoulmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Boolean existsByUserAndPost(User user, Post post);
}
