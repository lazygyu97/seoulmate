package com.sparta.seoulmate.openApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
@Getter
@NoArgsConstructor
public class ItemDto {
//    private String list_total_count;
//    private String CODE;
//    private String MESSAGE;
    private String GUBUN;//서비스구분
    private String SVCID;//서비스ID
    private String MAXCLASSNM;//대분류명
    private String MINCLASSNM;//소분류명
    private String SVCSTATNM;//서비스상태
    private String SVCNM;//서비스명
    private String PAYATNM;//결제방법
    private String PLACENM;//장소명
    private String USETGTINFO;//서비스대상
    private String SVCURL;//바로가기 url
    private String X;//장소 x좌표
    private String Y;//장소 y좌표
    private String SVCOPNBGNDT;	//서비스개시시작일시
    private String SVCOPNENDDT;	//서비스개시종료일시
    private String RCPTBGNDT;	//접수시작일시
    private String RCPTENDDT;	//접수종료일시
    private String AREANM;	//지역명
    private String IMGURL;	//이미지경로
    private String DTLCONT;	//상세내용
    private String TELNO;	//전화번호
    private String V_MIN;	//서비스이용 시작시간
    private String V_MAX;	//서비스이용 종료시간
    private String REVSTDDAYNM;	//취소기간 기준정보
    private String REVSTDDAY;	//취소기간 기준일까지

    public ItemDto(JSONObject itemJson) {
//        this.list_total_count = itemJson.getString("list_total_count");
//        this.CODE = itemJson.getString("CODE");
//        this.MESSAGE = itemJson.getString("MESSAGE");
        this.GUBUN = itemJson.getString("GUBUN");
        this.SVCID = itemJson.getString("SVCID");
        this.MAXCLASSNM = itemJson.getString("MAXCLASSNM");
        this.MINCLASSNM = itemJson.getString("MINCLASSNM");
        this.SVCSTATNM = itemJson.getString("SVCSTATNM");
        this.SVCNM = itemJson.getString("SVCNM");
        this.PAYATNM = itemJson.getString("PAYATNM");
        this.PLACENM = itemJson.getString("PLACENM");
        this.USETGTINFO = itemJson.getString("USETGTINFO");
        this.SVCURL = itemJson.getString("SVCURL");
        this.X =  itemJson.getString("X");
        this.Y = itemJson.getString("Y");
        this.SVCOPNBGNDT = itemJson.getString("SVCOPNBGNDT");
        this.SVCOPNENDDT = itemJson.getString("SVCOPNENDDT");
        this.RCPTBGNDT = itemJson.getString("RCPTBGNDT");
        this.RCPTENDDT = itemJson.getString("RCPTENDDT");
        this.AREANM = itemJson.getString("AREANM");
        this.IMGURL = itemJson.getString("IMGURL");
        this.DTLCONT = itemJson.getString("DTLCONT");
        this.TELNO = itemJson.getString("TELNO");
        this.V_MIN = itemJson.getString("V_MIN");
        this.V_MAX = itemJson.getString("V_MAX");
        this.REVSTDDAYNM = itemJson.getString("REVSTDDAYNM");
        this.REVSTDDAY = itemJson.getString("REVSTDDAY");
    }


}
