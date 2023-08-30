package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/commentLike/{commentId}")
    public ResponseEntity<ApiResponseDto> onClickCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) throws Exception {
        commentLikeService.onClickCommentLike(userDetails, commentId);
        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/commentLike/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId){
        commentLikeService.deleteCommentLike(userDetails, commentId);
        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 취소 성공", HttpStatus.OK.value()));
    }

}
