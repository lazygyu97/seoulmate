package com.sparta.seoulmate.dto.post;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PostResponseDto extends ApiResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;
    private List<PostImageResponseDto> images;
    private int postLikes;
    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(post.getComments().stream().map(CommentResponseDto::of).toList())
                .images(post.getImages().stream().map(PostImageResponseDto::of).toList())
                .postLikes(post.getPostLikes().size())
                .build();
    }
}
