package com.sparta.seoulmate.openApi.service;

import com.sparta.seoulmate.openApi.dto.DetailItemDto;
import com.sparta.seoulmate.openApi.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final String API_KEY = "5a65746f7170617238384d474c4e4d";
    private final String CULTURE_PATH = "/json/ListPublicReservationCulture";
    private final String DETAIL_PATH = "/json/ListPublicReservationDetail";
    private final String START_INDEX = "/1";
    private final String END_INDEX = "/5";

    public OpenApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }


    public List<ItemDto> getAllCultureService() {

        URI uri = UriComponentsBuilder
            .fromUriString("http://openapi.seoul.go.kr:8088")
            .path(API_KEY)
            .path(CULTURE_PATH)
            .path(START_INDEX)
            .path(END_INDEX)
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();

        log.info("uri = " + uri);
        RequestEntity<Void> requestEntity = RequestEntity
            .get(uri)
            .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        log.info("OPEN API Status Code : " + responseEntity.getStatusCode());

        return fromJSONtoItems(responseEntity.getBody());

    }
    public List<DetailItemDto> getServiceDetail() {
        URI uri = UriComponentsBuilder
            .fromUriString("http://openapi.seoul.go.kr:8088")
            .path(API_KEY)
            .path(DETAIL_PATH)
            .path(START_INDEX)
            .path(END_INDEX)
            .path("/S230214105405760799")
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();

        log.info("uri = " + uri);
        RequestEntity<Void> requestEntity = RequestEntity
            .get(uri)
            .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        log.info("OPEN API Status Code : " + responseEntity.getStatusCode());

        return fromJSONtoItems2(responseEntity.getBody());

    }
    public List<ItemDto> fromJSONtoItems(String responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONArray items  = jsonObject.getJSONObject("ListPublicReservationCulture").getJSONArray("row");
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            ItemDto itemDto = new ItemDto((JSONObject) item);
            itemDtoList.add(itemDto);
            log.info("서비스이름: "+itemDto.getSVCNM());
        }

        return itemDtoList;
    }
    public List<DetailItemDto> fromJSONtoItems2(String responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONArray items  = jsonObject.getJSONObject("ListPublicReservationDetail").getJSONArray("row");
        List<DetailItemDto> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            DetailItemDto itemDto = new DetailItemDto((JSONObject) item);
            itemDtoList.add(itemDto);
            log.info("서비스이름: "+itemDto.getSVCNM());
        }

        return itemDtoList;
    }


}
