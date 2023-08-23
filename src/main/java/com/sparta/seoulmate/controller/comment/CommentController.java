package com.sparta.seoulmate.controller.comment;

import com.sparta.seoulmate.dto.comment.CommentRequestDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
