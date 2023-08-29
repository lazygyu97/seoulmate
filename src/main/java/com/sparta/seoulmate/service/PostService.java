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

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 게시글 생성
    public PostResponseDto createPost(PostRequestDto requestDto, User author) {
        Post post = requestDto.toEntity(author);

        postRepository.save(post);
        return PostResponseDto.of(post);
    }

    // 전체 게시글 목록 조회
    public PostListResponseDto getPosts() {
        List<Post> postList = postRepository.findAll();

        return PostListResponseDto.of(postList);
    }

    // 게시글 단건 조회
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return PostResponseDto.of(post);
    }

    // 게시글 업데이트
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User author) {
        Post post = findPost(id);

        if (!(author.getRole().equals(UserRoleEnum.ADMIN) || post.getAuthor().equals(author))) {
            throw new RejectedExecutionException();
        }

        post.updateTitle(requestDto.getTitle());
        post.updateContent(requestDto.getContent());

        return PostResponseDto.of(post);
    }

    // 게시글 삭제
    public void deletePost(Long id, User author) {
        Post post = findPost(id);

        if (!(author.getRole().equals(UserRoleEnum.ADMIN) || post.getAuthor().equals(author))) {
            throw new RejectedExecutionException();
        }
        postRepository.delete(post);
    }

    // 게시글 Entity 단건 조회
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
