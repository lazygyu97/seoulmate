package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.Post;
import lombok.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostListResponseDto {
    private List<PostResponseDto> postsList;

    public static PostListResponseDto of(List<Post> posts) {
        List<PostResponseDto> postResponseDtos = posts.stream().map(PostResponseDto::of).toList();
        return PostListResponseDto.builder()
                .postsList(postResponseDtos)
                .build();
    }
}
