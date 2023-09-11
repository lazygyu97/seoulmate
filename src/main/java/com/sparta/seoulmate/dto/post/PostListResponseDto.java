package com.sparta.seoulmate.dto.post;

import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponseDto {
    private List<PostResponseDto> postList;
    public static PostListResponseDto of (List<Post> posts){
        List<PostResponseDto> postResponseDtos = posts.stream().map(PostResponseDto::of).toList();
        return PostListResponseDto.builder()
                .postList(postResponseDtos)
                .build();
    }
}
