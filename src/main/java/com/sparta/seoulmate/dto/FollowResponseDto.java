package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowResponseDto {
    private Long id;
    private String nickname;
    private String image;

    public static FollowResponseDto of(User user) {
        return FollowResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .image(user.getImage())
                .build();
    }
}
