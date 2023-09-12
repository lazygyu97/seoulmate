package com.sparta.seoulmate.openApi.dto;

import com.sparta.seoulmate.dto.post.PostLikeResponseDto;
import com.sparta.seoulmate.entity.SeoulApi;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemResponseDto {

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
    private String SVCOPNBGNDT;//서비스개시시작일시
    private String SVCOPNENDDT;//서비스개시종료일시
    private String RCPTBGNDT;//접수시작일시
    private String RCPTENDDT;//접수종료일시
    private String AREANM;//지역명
    private String IMGURL;//이미지경로
    private String TELNO;//전화번호
    private String V_MIN;//서비스이용 시작시간
    private String V_MAX;//서비스이용 종료시간
    private String REVSTDDAYNM;//취소기간 기준정보
    private String REVSTDDAY;//취소기간 기준일까지
    private List<ApiLikeResponseDto> seoulApiLikes;//취소기간 기준일까지


    public static ItemResponseDto of(SeoulApi seoulApi) {
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .GUBUN(seoulApi.getGUBUN())
                .SVCID(seoulApi.getSVCID())
                .MAXCLASSNM(seoulApi.getMAXCLASSNM())
                .MINCLASSNM(seoulApi.getMINCLASSNM())
                .SVCSTATNM(seoulApi.getSVCSTATNM())
                .SVCNM(seoulApi.getSVCNM())
                .PAYATNM(seoulApi.getPAYATNM())
                .PLACENM(seoulApi.getPLACENM())
                .USETGTINFO(seoulApi.getUSETGTINFO())
                .SVCURL(seoulApi.getSVCURL())
                .X(seoulApi.getX())
                .Y(seoulApi.getY())
                .SVCOPNBGNDT(seoulApi.getSVCOPNBGNDT())
                .SVCOPNENDDT(seoulApi.getSVCOPNENDDT())
                .RCPTBGNDT(seoulApi.getRCPTBGNDT())
                .RCPTENDDT(seoulApi.getRCPTENDDT())
                .AREANM(seoulApi.getAREANM())
                .IMGURL(seoulApi.getIMGURL())
                .TELNO(seoulApi.getTELNO())
                .V_MIN(seoulApi.getV_MIN())
                .V_MAX(seoulApi.getV_MAX())
                .REVSTDDAYNM(seoulApi.getREVSTDDAYNM())
                .REVSTDDAY(seoulApi.getREVSTDDAY())
                .seoulApiLikes(seoulApi.getSeoulApiLikes().stream().map(ApiLikeResponseDto::of).toList())
                .build();
        return itemResponseDto;
    }


}
