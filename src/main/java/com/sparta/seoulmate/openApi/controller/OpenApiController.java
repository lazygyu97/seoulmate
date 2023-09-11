package com.sparta.seoulmate.openApi.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.openApi.dto.ItemListResponseDto;
import com.sparta.seoulmate.openApi.dto.ItemResponseDto;
import com.sparta.seoulmate.openApi.service.OpenApiService;
import com.sparta.seoulmate.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seoul")
public class OpenApiController {

    private final OpenApiService openApiService;

    // 접수중 서비스 전체 가져오기
    @GetMapping("/services")
    public ResponseEntity<ItemListResponseDto> getAllService() {
        ItemListResponseDto result = openApiService.getAllService();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/services/recommend")
    public ResponseEntity<ItemListResponseDto> getRecommendService(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ItemListResponseDto result = openApiService.getRecommendService(userDetails.getUser());

        return ResponseEntity.ok().body(result);
    }

    //서비스 데이터 단건 조회
    @GetMapping("/service")
    public ResponseEntity<ItemResponseDto> getService(@RequestParam("svcid") String svcid) {
        ItemResponseDto result = openApiService.getService(svcid);
        return ResponseEntity.ok().body(result);
    }

    //서비스 데이터 단건 조회
    @PostMapping("/service/like")
    public ResponseEntity<ApiResponseDto> likeService(@RequestParam("svcid") String svcid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        openApiService.likeService(svcid, userDetails.getUser());
        return ResponseEntity.status(201).body(new ApiResponseDto("서비스 좋아요 성공", HttpStatus.CREATED.value()));
    }

    @DeleteMapping("/service/like")
    public ResponseEntity<ApiResponseDto> deleteLikeService(@RequestParam("svcid") String svcid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        openApiService.deleteLikeService(svcid, userDetails.getUser());
        return ResponseEntity.status(201).body(new ApiResponseDto("서비스 좋아요 해제 성공", HttpStatus.CREATED.value()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto> updateDatabase() {
        openApiService.getCountAllService();
        return ResponseEntity.status(201).body(new ApiResponseDto("데이터베이스 업데이트 성공", HttpStatus.CREATED.value()));
    }
}
