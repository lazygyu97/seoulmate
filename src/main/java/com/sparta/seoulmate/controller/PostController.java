package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.dto.PostRequestDto;
import com.sparta.seoulmate.dto.PostResponseDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.PostService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<ApiResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestBody PostRequestDto requestDto) {

        postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new ApiResponseDto("게시글 생성 성공!", HttpStatus.OK.value()));
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        Page<PostResponseDto> result = postService.getPosts(page-1, size, sortBy, isAsc);
        return ResponseEntity.ok().body(result);
    }

    // 게시글 단건 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }

    // 게시글 업데이트
    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long id,
                                                     @RequestBody PostRequestDto requestDto) {

        try {
            PostResponseDto result = postService.updatePost(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long id) {
        try {
            postService.deletePost(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공,", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 좋아요
    @PostMapping("/posts/{id}/likes")
    public ResponseEntity<ApiResponseDto> likePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        try {
            postService.likePost(id, userDetails.getUser());
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 성공", HttpStatus.ACCEPTED.value()));
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/posts/{id}/likes")
    public ResponseEntity<ApiResponseDto> deleteLikePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        try {
            postService.deleteLikePost(id, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 취소 성공", HttpStatus.ACCEPTED.value()));
    }
}
