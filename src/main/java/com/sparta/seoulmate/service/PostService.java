package com.sparta.seoulmate.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    /**
     * 게시글 생성
     *
     * @param requestDto 게시글 생성 요청정보
     * @param author     게시글 생성 요청자
     * @return 게시글 생성 결과
     */
    public PostResponseDto createPost(PostRequestDto requestDto, User author) {
        Post post = requestDto.toEntity(author);

        postRepository.save(post);
        return PostResponseDto.of(post);
    }

    /**
     * 지정된 페이지 번호와 크기(page, size), 정렬 조건(sortBy, isAsc)에 따라 게시글을 조회하여 페이지 단위로 반환
     *
     * @param page   조회하고자 하는 페이지 번호 (0번 부터 시작)
     * @param size   한 페이지당 보여줄 게시글의 개수
     * @param sortBy 정렬 기준 필드명
     * @param isAsc  정렬 순서 - true 면 오름차순(asc), false 면 내림차순(desc)
     * @return 지정된 페이지 번호, 게시글 개수, 정렬 기준, 정렬 순서에 맞는 게시글 목록
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(PostResponseDto::of);
    }

    /**
     * 게시글을 검색하고 검색 결과를 페이지로 반환
     *
     * @param keyword  검색어
     * @param criteria criteria 검색 조건 (title, content, title+content 중 하나)
     * @param pageable pageable 페이징 정보
     * @return 검색 결과의 페이지(PostResponseDto)
     */
    public Page<PostResponseDto> searchPosts(String keyword, String criteria, Pageable pageable) {
        if ("title".equals(criteria)) {
            return postRepository.findByTitleContaining(keyword, pageable)
                    .map(PostResponseDto::of);
        } else if ("content".equals(criteria)) {
            return postRepository.findByContentContaining(keyword, pageable)
                    .map(PostResponseDto::of);
        } else if ("title+content".equals(criteria)) {
            return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)
                    .map(PostResponseDto::of);
        } else {
            throw new IllegalArgumentException("Invalid criteria");
        }
    }

    /**
     * 게시글 단건 조회
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 정보
     */
    public PostResponseDto getPostById(Long id) {
        Post post = findPost(id);

        return PostResponseDto.of(post);
    }

    /**
     * 게시글 업데이트
     *
     * @param id         조회할 게시글 ID
     * @param requestDto 업데이트 할 게시글 정보
     * @param author     게시글 업데이트 요청자
     * @return 업데이트된 게시글 정보
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
     *
     * @param id     삭제할 게시글 ID
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
     *
     * @param id   좋아요 누를 게시글의 ID
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
     *
     * @param id   좋아요 취소할 게시글의 ID
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
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 Entity
     */
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }


}
