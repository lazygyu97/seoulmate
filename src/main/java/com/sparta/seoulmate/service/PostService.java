package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.PostListResponseDto;
import com.sparta.seoulmate.dto.PostRequestDto;
import com.sparta.seoulmate.dto.PostResponseDto;
import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);
        return new PostResponseDto(post);
    }


    public PostListResponseDto getPosts() {
        List<PostResponseDto> postList = postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return new PostListResponseDto(postList);
    }


    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }


    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post = findPost(id);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || post.getAuthor().equals(user))) {
            throw new RejectedExecutionException();
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, User user) {
        Post post = findPost(id);

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || post.getAuthor().equals(user))) {
            throw new RejectedExecutionException();
        }

        postRepository.delete(post);
    }



    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
