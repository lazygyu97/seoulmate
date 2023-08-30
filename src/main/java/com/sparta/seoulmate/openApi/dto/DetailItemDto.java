package com.sparta.seoulmate.openApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Getter
@NoArgsConstructor
public class DetailItemDto {

    private String SVCID; //서비스번호
    private String SVCNM; //서비스명
    private String FEEGUIDURL; //요금안내URL
    private String SVCBEGINDT; //이용시작일자
    private String SVCENDDT; //이용종료일자
    private String PLACESN; //장소순번
    private String PLACENM; //장소명
    private String SUBPLACENM; //상세장소
    private String PAYAT; //유료여부
    private String RCPTMTHD; //접수방법코드
    private String RCEPTMTH_NM; //접수방법명
    private String RCEPTBEGDT; //접수시작일
    private String RCEPTENDDT; //접수종료일
    private String RCRPERCAP;// 모집정원
    private String UNITCODE;// 모집정원단위코드
    private String UNICODE_NM;//모집정원단위명
    private String SELMTHDCODE;// 선별방법코드
    private String SELMTHDCODE_NM;// 선별방법명
    private String SVCENDTELNO;// 서비스담당전화번호
    private String SVCENDUSRSN;// 서비스담당자번호
    private String ORGNM;//서비스담당기관명
    private String ONEREQMINPR;// 신청제한최소정원수
    private String ONEREQMXMPR;// 신청제한최대정원수
    private String SVCSTTUS;// 서비스상태코드
    private String SVCSTTUS_NM;// 서비스상태코드명
    private String REVSTDDAY;// 취소기준일
    private String CODE;// 분류코드
    private String CODENM;//분류코드명
    private String SMCODE;// 상위분류코드
    private String SMCODE_NM;// 상위분류코드명
    private String WAITNUM;// 대기가능수
    private String USETIMEUNITCODE;// 이용시간단위코드
    private String USETIMEUNITCODE_NM;// 이용시간단위명
    private String USEDAYSTDRCPTDAY;// 이용일기준접수일
    private String USEDAYSTDRCPTTIME;// 이용일기준접수시간
    private String RSVDAYSTDRCPTDAY;// 예약일기준접수일
    private String RSVDAYSTDRCPTTIME;// 예약일기준접수시간
    private String USELIMMINNOP;// 사용제한최소인원
    private String USELIMMAXNOP;// 사용제한최대인원
    private String EXTINFO;// 면적
    private String X;// 장소X좌표
    private String Y;// 장소Y좌표
    private String ADRES;// 장소주소
    private String TELNO;// 장소전화번호
    private String SVCOPNBGNDT;// 서비스개시시작일시
    private String SVCOPNENDDT;// 서비스개시종료일시
    private String RCPTBGNDT;// 접수시작일시
    private String RCPTENDDT;// 접수종료일시
    private String AREANM;// 지역명
    private String NOTICE;// 주의사항
    private String IMG_PATH;// 이미지경로
    private String DTLCONT;// 상세내용
    private String V_MAX;// 서비스이용종료시간
    private String V_MIN;// 서비스이용시작시간
    private String REVSTDDAYNM;// 취소기간기준정보
    public DetailItemDto(JSONObject itemJson) {
        this.SVCID = itemJson.getString("SVCID");
        this.SVCNM = itemJson.getString("SVCNM");
        this.FEEGUIDURL = itemJson.getString("FEEGUIDURL");
        this.SVCBEGINDT = itemJson.getString("SVCBEGINDT");
        this.SVCENDDT = itemJson.getString("SVCENDDT");
        this.PLACESN = itemJson.getString("PLACESN");
        this.PLACENM = itemJson.getString("PLACENM");
        this.SUBPLACENM = itemJson.getString("SUBPLACENM");
        this.PAYAT = itemJson.getString("PAYAT");
        this.RCPTMTHD = itemJson.getString("RCPTMTHD");
        this.RCEPTMTH_NM = itemJson.getString("RCEPTMTH_NM");
        this.RCEPTBEGDT = itemJson.getString("RCEPTBEGDT");
        this.RCEPTENDDT = itemJson.getString("RCEPTENDDT");
//        this.RCRPERCAP = itemJson.getBigDecimal("RCRPERCAP");
        this.UNITCODE = itemJson.getString("UNITCODE");
        this.UNICODE_NM = itemJson.getString("UNICODE_NM");
        this.SELMTHDCODE = itemJson.getString("SELMTHDCODE");
        this.SELMTHDCODE_NM = itemJson.getString("SELMTHDCODE_NM");
        this.SVCENDTELNO = itemJson.getString("SVCENDTELNO");
        this.SVCENDUSRSN = itemJson.getString("SVCENDUSRSN");
        this.ORGNM = itemJson.getString("ORGNM");
//        this.ONEREQMINPR = itemJson.getString("ONEREQMINPR");
//        this.ONEREQMXMPR = itemJson.getString("ONEREQMXMPR");
        this.SVCSTTUS = itemJson.getString("SVCSTTUS");
        this.SVCSTTUS_NM = itemJson.getString("SVCSTTUS_NM");
        this.REVSTDDAY = itemJson.getString("REVSTDDAY");
        this.CODE = itemJson.getString("CODE");
        this.CODENM = itemJson.getString("CODENM");
        this.SMCODE = itemJson.getString("SMCODE");
        this.SMCODE_NM = itemJson.getString("SMCODE_NM");
//        this.WAITNUM = itemJson.getString("WAITNUM");
        this.USETIMEUNITCODE = itemJson.getString("USETIMEUNITCODE");
        this.USETIMEUNITCODE_NM = itemJson.getString("USETIMEUNITCODE_NM");
        this.USEDAYSTDRCPTDAY = itemJson.getString("USEDAYSTDRCPTDAY");
        this.USEDAYSTDRCPTTIME = itemJson.getString("USEDAYSTDRCPTTIME");
        this.RSVDAYSTDRCPTDAY = itemJson.getString("RSVDAYSTDRCPTDAY");
        this.RSVDAYSTDRCPTTIME = itemJson.getString("RSVDAYSTDRCPTTIME");
//        this.USELIMMINNOP = itemJson.getString("USELIMMINNOP");
//        this.USELIMMAXNOP = itemJson.getString("USELIMMAXNOP");
        this.EXTINFO = itemJson.getString("EXTINFO");
        this.X = itemJson.getString("X");
        this.Y = itemJson.getString("Y");
        this.ADRES = itemJson.getString("ADRES");
        this.TELNO = itemJson.getString("TELNO");
        this.SVCOPNBGNDT = itemJson.getString("SVCOPNBGNDT");
        this.SVCOPNENDDT = itemJson.getString("SVCOPNENDDT");
        this.RCPTBGNDT = itemJson.getString("RCPTBGNDT");
        this.RCPTENDDT = itemJson.getString("RCPTENDDT");
        this.AREANM = itemJson.getString("AREANM");
        this.NOTICE = itemJson.getString("NOTICE");
        this.IMG_PATH = itemJson.getString("IMG_PATH");
        this.DTLCONT = itemJson.getString("DTLCONT");
        this.V_MAX = itemJson.getString("V_MAX");
        this.V_MIN = itemJson.getString("V_MIN");
        this.REVSTDDAYNM = itemJson.getString("REVSTDDAYNM");
    }

}
