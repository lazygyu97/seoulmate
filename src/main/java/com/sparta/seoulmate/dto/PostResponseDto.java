package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PostResponseDto extends ApiResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
