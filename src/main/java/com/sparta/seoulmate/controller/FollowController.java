package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.dto.PostResponseDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;
  
    @GetMapping
    public String follow(){
        return "follow";
    } // follow.html을 반환
    

    // 나를 팔로우 한 사람, 내가 팔로우 한 사람, 내가 팔로우 한 사람의 포스트 확인하기
    @GetMapping("/posts")
    @ResponseBody
    public ResponseEntity<List<PostResponseDto>> viewFollowingPostList(@AuthenticationPrincipal UserDetailsImpl userDetail){
        List<PostResponseDto> postResponseDto = followService.viewFollowingPostList(userDetail.getUser());
        return ResponseEntity.ok().body(postResponseDto);
    }

    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> following(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId) {
        String nickname = followService.following(userDetails, userId);
        return ResponseEntity.ok().body(new ApiResponseDto(nickname + " 님을 팔로우 했습니다", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> unfollowing(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId) {
        String nickname = followService.unfollowing(userDetails, userId);
        return ResponseEntity.ok().body(new ApiResponseDto( nickname + " 님을 팔로우 해제 했습니다", HttpStatus.OK.value()));
    }

}
