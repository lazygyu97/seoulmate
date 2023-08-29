package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.PostListResponseDto;
import com.sparta.seoulmate.dto.PostRequestDto;
import com.sparta.seoulmate.dto.PostResponseDto;
import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.PostLike;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.repository.PostLikeRepository;
import com.sparta.seoulmate.repository.PostRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    /**
     * 게시글 생성
     * @param requestDto 게시글 생성 요청정보
     * @param author 게시글 생성 요청자
     * @return 게시글 생성 결과
     */
    public PostResponseDto createPost(PostRequestDto requestDto, User author) {
        Post post = requestDto.toEntity(author);

        postRepository.save(post);
        return PostResponseDto.of(post);
    }

    /**
     * 전체 게시글 목록 조회
     * @return 전체 게시글 목록
     */
    public PostListResponseDto getPosts() {
        List<Post> postList = postRepository.findAll();

        return PostListResponseDto.of(postList);
    }

    /**
     * 게시글 단건 조회
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 정보
     */
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return PostResponseDto.of(post);
    }

    /**
     * 게시글 업데이트
     * @param id 조회할 게시글 ID
     * @param requestDto 업데이트 할 게시글 정보
     * @param author 게시글 업데이트 요청자
     * @return
     */
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

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글 ID
     * @param author 게시글 삭제 요청자
     */
    public void deletePost(Long id, User author) {
        Post post = findPost(id);

        if (!(author.getRole().equals(UserRoleEnum.ADMIN) || post.getAuthor().equals(author))) {
            throw new RejectedExecutionException();
        }
        postRepository.delete(post);
    }

    /**
     * 게시글 좋아요
     * @param id 좋아요 누를 게시글의 ID
     * @param user 게시글 좋아요 요청자
     */
    @Transactional
    public void likePost(Long id, User user) {
        Post post = findPost(id);

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateRequestException("이미 좋아요 한 게시글 입니다.");
        } else {
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
    }

    /**
     * 게시글 좋아요 취소
     * @param id 좋아요 취소할 게시글의 ID
     * @param user 게시글 좋아요 취소 요청자
     */
    @Transactional
    public void deleteLikePost(Long id, User user) {
        Post post = findPost(id);
        Optional<PostLike> postLikeOptional = postLikeRepository.findByUserAndPost(user, post);
        if (postLikeOptional.isPresent()) {
            postLikeRepository.delete(postLikeOptional.get());
        } else {
            throw new IllegalArgumentException("해당 게시글에 취소할 좋아요가 없습니다.");
        }
    }

    /**
     * 게시글 Entity 단건 조회
     * @param id 조회할 게시글 ID
     * @return 게시글 Entity
     */
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
}
