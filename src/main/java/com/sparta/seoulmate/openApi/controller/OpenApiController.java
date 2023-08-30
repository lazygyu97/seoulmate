package com.sparta.seoulmate.openApi.controller;

import com.sparta.seoulmate.openApi.dto.ItemListResponseDto;
import com.sparta.seoulmate.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OpenApiController {

    private final OpenApiService openApiService;

    // 접수중인 문화체험 서비스 전체 가져오기
    @GetMapping("/culture")
    public ItemListResponseDto getAllCultureService() {
        return openApiService.getAllCultureService();
    }
}
