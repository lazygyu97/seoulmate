package com.sparta.seoulmate.openApi.dto;

import com.sparta.seoulmate.entity.SeoulApi;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
@Getter
@NoArgsConstructor
public class UpdateItemDto {
    // 스케줄링을 통한 데이터베이스 업데이트를 위한 dto
    public SeoulApi toEntity(JSONObject itemJson) {
        SeoulApi seoulApi = SeoulApi.builder()
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
                .TELNO(itemJson.getString("TELNO"))
                .V_MIN(itemJson.getString("V_MIN"))
                .V_MAX(itemJson.getString("V_MAX"))
                .REVSTDDAYNM(itemJson.getString("REVSTDDAYNM"))
                .REVSTDDAY(itemJson.getString("REVSTDDAY"))
                .build();
        return seoulApi;
    }


}
