package com.sparta.seoulmate.openApi.scheduler;

import com.sparta.seoulmate.openApi.dto.UpdateItemDto;
import com.sparta.seoulmate.openApi.repository.SeoulApiRepository;
import com.sparta.seoulmate.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "SeoulApiScheduler")
@Component
@RequiredArgsConstructor
public class SeoulApiScheduler {

    private final OpenApiService openApiService;
    private final RestTemplate restTemplate;
    private final SeoulApiRepository seoulApiRepository;

    private final String API_KEY = "5a65746f7170617238384d474c4e4d/json/";
    //    크론 스케줄링
    //    첫 번째 필드: 초 (0-59)
    //    두 번째 필드: 분 (0-59)
    //    세 번째 필드: 시간 (0-23)
    //    네 번째 필드: 일 (1-31)
    //    다섯 번째 필드: 월 (1-12)
    //    여섯 번째 필드: 요일 (0-6, 일요일부터 토요일까지, 일요일=0 또는 7)

    //  공공 서비스에서 제공하는 데이터베이스를 새벽 6시와 저녁 11 마다 업데이트 하는 메서드
    @Scheduled(cron = "0 0 6,23 * * *") // 새벽 6시와 저녁 11
    public void updateDatabase() {
        openApiService.getCountAllService();
    }
}
