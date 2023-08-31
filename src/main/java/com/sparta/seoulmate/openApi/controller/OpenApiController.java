package com.sparta.seoulmate.openApi.controller;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.openApi.dto.ItemListResponseDto;
import com.sparta.seoulmate.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seoul")
public class OpenApiController {

    private final OpenApiService openApiService;

    // 접수중인 문화체험 서비스 전체 가져오기
    @GetMapping("/culture")
    public ItemListResponseDto getAllCultureService() {
        return openApiService.getAllCultureService();
    }
    // 접수중인 교육강좌 서비스 전체 가져오기
    @GetMapping("/education")
    public ItemListResponseDto getAllEducationService() {
        return openApiService.getAllEducationService();
    }
    // 접수중인 진료복지 서비스 전체 가져오기
    @GetMapping("/medical")
    public ItemListResponseDto getAllMedicalService() {
        return openApiService.getAllMedicalService();
    }
    // 접수중인 채육시설 서비스 전체 가져오기
    @GetMapping("/sport")
    public ItemListResponseDto getAllSportService() {
        System.out.println("체육");
        return openApiService.getAllSportService();
    }
    // 접수중인 공간시설 서비스 전체 가져오기
    @GetMapping("/space")
    public ItemListResponseDto getAllSpaceService() {
        return openApiService.getAllSpaceService();
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto> updateDatabase() {
        openApiService.getCountAllService();
        return ResponseEntity.status(201).body(new ApiResponseDto("데이터베이스 업데이트 성공", HttpStatus.CREATED.value()));
    }
}
