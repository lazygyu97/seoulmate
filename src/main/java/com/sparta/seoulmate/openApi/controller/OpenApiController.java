package com.sparta.seoulmate.openApi.controller;

import com.sparta.seoulmate.openApi.dto.DetailItemDto;
import com.sparta.seoulmate.openApi.dto.ItemDto;
import com.sparta.seoulmate.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OpenApiController {
    private final OpenApiService openApiService;
    @GetMapping("/culture")
    public List<ItemDto> getAllCultureService(){

        return openApiService.getAllCultureService();
    }
    @GetMapping("/detail")
    public List<DetailItemDto> getServiceDetail(){

        return openApiService.getServiceDetail();
    }
}
