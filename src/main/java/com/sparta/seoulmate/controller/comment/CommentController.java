package com.sparta.seoulmate.controller.comment;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.dto.comment.CommentRequestDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // Comment 생성
    @PostMapping("/comment")
    public String createComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute CommentRequestDto commentRequestDto) {
        commentService.createComment(postId, userDetails.getUser(), commentRequestDto);
        return "redirect:/api/post/" + commentRequestDto.getPostId();
    }

    // Comment 단건 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto commentResponseDto = commentService.getComment(commentId);
        return ResponseEntity.ok().body(commentResponseDto);
    }

    // Comment 다건 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<CommentResponseDto> commentResponseDtos = commentService.getComments();
        return ResponseEntity.ok().body(commentResponseDtos);
    }

    // Comment 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(commentId, userDetails.getUser(), commentRequestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 수정 성공 !", HttpStatus.OK.value()));
    }
}
