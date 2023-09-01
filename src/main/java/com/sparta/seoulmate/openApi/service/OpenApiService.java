package com.sparta.seoulmate.openApi.service;

import com.sparta.seoulmate.entity.SeoulApi;
import com.sparta.seoulmate.openApi.dto.ItemListResponseDto;
import com.sparta.seoulmate.openApi.dto.UpdateItemDto;
import com.sparta.seoulmate.openApi.repository.SeoulApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

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


    // 접수중인 문화체험 서비스 전체 가져오기
    public ItemListResponseDto getAllCultureService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("문화체험", "접수중");

        return ItemListResponseDto.of(list);

    }
    // 접수중인 체육시설 전체 가져오기
    public ItemListResponseDto getAllSportService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("체육시설", "접수중");

        return ItemListResponseDto.of(list);

    }
    // 접수중인 의료시설 전체 가져오기
    public ItemListResponseDto getAllMedicalService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("진료복지", "접수중");

        return ItemListResponseDto.of(list);

    }
    // 접수중인 공간시설 전체 가져오기
    public ItemListResponseDto getAllSpaceService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("공간시설", "접수중");

        return ItemListResponseDto.of(list);

    }
    // 접수중인 교육강좌 전체 가져오기
    public ItemListResponseDto getAllEducationService() {

        List<SeoulApi> list = seoulApiRepository.findByMAXCLASSNMAndSVCSTATNM("교육강좌", "접수중");

        return ItemListResponseDto.of(list);

    }

    //각 카테고리의 데이터 개수 가져오기 (updateDatabase()에서 전체 데이터 베이스를 업데이트 할때 사용.)
    public void getCountAllService() {
        List<String> serviceList = Arrays.asList(SPORT_PATH, INSTITUTION_PATH, CULTURE_PATH, EDUCATION_PATH, MEDICAL_PATH);
        HashMap<String, Integer> countMap = new HashMap<>();
        try {
            for (String service : serviceList) {

                URI uri = UriComponentsBuilder
                        .fromUriString("http://openapi.seoul.go.kr:8088/" + API_KEY + service + "/1/1")
                        .encode(StandardCharsets.UTF_8)
                        .build()
                        .toUri();
                log.info("uri = " + uri);
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
                log.info("OPEN API Status Code : " + responseEntity.getStatusCode());

                JSONObject jsonObject = new JSONObject(responseEntity.getBody()).getJSONObject(service);

                int listTotalCount = jsonObject.getInt("list_total_count");

                System.out.println(listTotalCount);

                countMap.put(service, listTotalCount);
            }
            updateAllDatabase(countMap);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("데이터베이스 업데이트에 실패했습니다.: total_count 를 불러오는 중에 문제가 발생했습니다.");

        }

    }
    // 각 카테고리 별 전체조회후 데이터베이스 업데이트
    @Transactional
    public void updateAllDatabase(HashMap<String, Integer> updateMap) {
        try {
            for (Map.Entry<String, Integer> entry : updateMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                log.info(key + " update processing...");

                URI uri = UriComponentsBuilder
                        .fromUriString("http://openapi.seoul.go.kr:8088/" + API_KEY + key)
                        .path("/1/" + value.toString())
                        .encode(StandardCharsets.UTF_8)
                        .build()
                        .toUri();
                log.info("uri = " + uri);

                ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
                log.info("OPEN API Status Code : " + responseEntity.getStatusCode());

                JSONObject jsonObject = new JSONObject(responseEntity.getBody()).getJSONObject(key);
                JSONArray itemsArray = jsonObject.getJSONArray("row");
                List<SeoulApi> itemsToSave = new ArrayList<>();

                for (Object item : itemsArray) {

                    JSONObject jsonObjectItem = (JSONObject) item;
                    String svcid = jsonObjectItem.getString("SVCID");

                    Optional<SeoulApi> updateData = seoulApiRepository.findBySVCID(svcid);

                    if (updateData.isPresent()) {
                        //새로 받아온 데이터가 mysql 데이터베이스 있는 경우.
                        //Transactional을 통한 Entity 업데이트
                        updateData.get().update((JSONObject) item);
                    } else {
                        //새로 받아온 데이터가 mysql에 없는 경우
                        //Repository를 통해 save 해준다.
                        itemsToSave.add(new UpdateItemDto().toEntity(jsonObjectItem));
                    }
                }
                seoulApiRepository.saveAll(itemsToSave);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("데이터베이스 업데이트에 실패했습니다. ");
        }

    }



}
