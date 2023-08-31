package com.sparta.seoulmate.openApi.service;

import com.sparta.seoulmate.entity.SeoulApi;
import com.sparta.seoulmate.openApi.dto.ItemListResponseDto;
import com.sparta.seoulmate.openApi.repository.SeoulApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final SeoulApiRepository seoulApiRepository;
    private final RestTemplate restTemplate;
    private final String API_KEY = "5a65746f7170617238384d474c4e4d/json/";
    private final String SPORT_PATH = "ListPublicReservationSport";
    private final String CULTURE_PATH = "ListPublicReservationCulture";
    private final String MEDICAL_PATH = "ListPublicReservationMedical";
    private final String EDUCATION_PATH = "ListPublicReservationEducation";
    private final String INSTITUTION_PATH = "ListPublicReservationInstitution";

//    private final String DETAIL_PATH = "/json/ListPublicReservationDetail";
//    private final String START_INDEX = "/1";
//    private final String END_INDEX = "/5";


    //각 카테고리의 데이터 개수 가져오기 (updateDatabase()에서 전체 데이터 베이스를 업데이트 할때 사용.)
    public HashMap<String, Integer> getCountAllService() throws InterruptedException {
        List<String> serviceList = Arrays.asList(SPORT_PATH, INSTITUTION_PATH, CULTURE_PATH, EDUCATION_PATH, MEDICAL_PATH);
        HashMap<String, Integer> countMap = new HashMap<>();

        for (String service : serviceList) {
            TimeUnit.SECONDS.sleep(1);
            URI uri = UriComponentsBuilder
                    .fromUriString("http://openapi.seoul.go.kr:8088/" + API_KEY + service + "/1/1")
                    .encode(StandardCharsets.UTF_8)
                    .build()
                    .toUri();
            log.info("uri = " + uri);

            RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            log.info("OPEN API Status Code : " + responseEntity.getStatusCode());

            JSONObject jsonObject = new JSONObject(responseEntity.getBody()).getJSONObject(service);

            int listTotalCount = jsonObject.getInt("list_total_count");

            System.out.println(listTotalCount);

            countMap.put(service, listTotalCount);
        }
        return countMap;
    }

    // 접수중인 문화체험 서비스 전체 가져오기
    public ItemListResponseDto getAllCultureService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("문화체험", "접수중");

        return ItemListResponseDto.of(list);

    }


}
