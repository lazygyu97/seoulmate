package com.sparta.seoulmate.dto.post;

import com.sparta.seoulmate.entity.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostImageResponseDto {
    private Long id;
    private String imageUrl;

    public static PostImageResponseDto of(Image image) {
        return PostImageResponseDto.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .build();
    }

}
