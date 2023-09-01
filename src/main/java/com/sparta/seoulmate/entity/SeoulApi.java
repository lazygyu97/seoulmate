package com.sparta.seoulmate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "seoul_apis")
public class SeoulApi {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 수동 방식... uuid ...!
//    private Long id;
// 서비스 ID를 나타내는 필드
    @Id
    private String SVCID;
    // 서비스 구분을 나타내는 필드
    @Column
    private String GUBUN;

    // 대분류명을 나타내는 필드
    @Column
    private String MAXCLASSNM;

    // 소분류명을 나타내는 필드
    @Column
    private String MINCLASSNM;

    // 서비스 상태명을 나타내는 필드
    @Column
    private String SVCSTATNM;

    // 서비스명을 나타내는 필드
    @Column
    private String SVCNM;

    // 결제 방법을 나타내는 필드
    @Column
    private String PAYATNM;

    // 장소명을 나타내는 필드
    @Column
    private String PLACENM;

    // 서비스 대상을 나타내는 필드
    @Column
    private String USETGTINFO;

    // 바로가기 URL을 나타내는 필드
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String SVCURL;

    // 장소 x좌표를 나타내는 필드
    @Column
    private String X;

    // 장소 y좌표를 나타내는 필드
    @Column
    private String Y;

    // 서비스 개시 시작일시를 나타내는 필드
    @Column
    private String SVCOPNBGNDT;

    // 서비스 개시 종료일시를 나타내는 필드
    @Column
    private String SVCOPNENDDT;

    // 접수 시작일시를 나타내는 필드
    @Column
    private String RCPTBGNDT;

    // 접수 종료일시를 나타내는 필드
    @Column
    private String RCPTENDDT;

    // 지역명을 나타내는 필드
    @Column
    private String AREANM;

    // 이미지 경로를 나타내는 필드
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String IMGURL;

    // 상세 내용을 나타내는 필드
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String DTLCONT;

    // 전화번호를 나타내는 필드
    @Column
    private String TELNO;

    // 서비스 이용 시작시간을 나타내는 필드
    @Column
    private String V_MIN;

    // 서비스 이용 종료시간을 나타내는 필드
    @Column
    private String V_MAX;

    // 취소기간 기준 정보를 나타내는 필드
    @Column
    private String REVSTDDAYNM;

    // 취소기간 기준일까지를 나타내는 필드
    @Column
    private String REVSTDDAY;

    public void update(JSONObject itemJson) {
            SeoulApi.builder()
                .GUBUN(itemJson.getString("GUBUN"))
                .SVCID(itemJson.getString("SVCID"))
                .MAXCLASSNM(itemJson.getString("MAXCLASSNM"))
                .MINCLASSNM(itemJson.getString("MINCLASSNM"))
                .SVCSTATNM(itemJson.getString("SVCSTATNM"))
                .SVCNM(itemJson.getString("SVCNM"))
                .PAYATNM(itemJson.getString("PAYATNM"))
                .PLACENM(itemJson.getString("PLACENM"))
                .USETGTINFO(itemJson.getString("USETGTINFO"))
                .SVCURL(itemJson.getString("SVCURL"))
                .X(itemJson.getString("X"))
                .Y(itemJson.getString("Y"))
                .SVCOPNBGNDT(itemJson.getString("SVCOPNBGNDT"))
                .SVCOPNENDDT(itemJson.getString("SVCOPNENDDT"))
                .RCPTBGNDT(itemJson.getString("RCPTBGNDT"))
                .RCPTENDDT(itemJson.getString("RCPTENDDT"))
                .AREANM(itemJson.getString("AREANM"))
                .IMGURL(itemJson.getString("IMGURL"))
                .DTLCONT(itemJson.getString("DTLCONT"))
                .TELNO(itemJson.getString("TELNO"))
                .V_MIN(itemJson.getString("V_MIN"))
                .V_MAX(itemJson.getString("V_MAX"))
                .REVSTDDAYNM(itemJson.getString("REVSTDDAYNM"))
                .REVSTDDAY(itemJson.getString("REVSTDDAY"))
                .build();
    }
}
