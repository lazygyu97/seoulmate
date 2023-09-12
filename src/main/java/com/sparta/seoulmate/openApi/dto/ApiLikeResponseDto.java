package com.sparta.seoulmate.openApi.dto;

import com.sparta.seoulmate.entity.PostLike;
import com.sparta.seoulmate.entity.SeoulApiLike;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiLikeResponseDto {
    private Long id;
    private String username;
    public static ApiLikeResponseDto of(SeoulApiLike seoulApiLike) {
        return ApiLikeResponseDto.builder()
                .id(seoulApiLike.getId())
                .username(seoulApiLike.getUser().getUsername())
                .build();
    }

}
