package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.dto.PostListResponseDto;
import com.sparta.seoulmate.dto.PostRequestDto;
import com.sparta.seoulmate.dto.PostResponseDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.PostService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/posts")
    public ResponseEntity<ApiResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestBody PostRequestDto requestDto) {

        postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new ApiResponseDto("카드 생성 성공!", HttpStatus.OK.value()));
    }




    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getPosts() {
        PostListResponseDto result = postService.getPosts();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto result = postService.getPostById(id);

        return ResponseEntity.ok().body(result);
    }







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

}
