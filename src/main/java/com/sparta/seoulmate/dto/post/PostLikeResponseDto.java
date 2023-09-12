package com.sparta.seoulmate.dto.post;

import com.sparta.seoulmate.entity.PostLike;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeResponseDto {
    private Long id;
    private String username;
    public static PostLikeResponseDto of(PostLike postLike) {
        return PostLikeResponseDto.builder()
                .id(postLike.getId())
                .username(postLike.getUser().getUsername())
                .build();
    }

}
