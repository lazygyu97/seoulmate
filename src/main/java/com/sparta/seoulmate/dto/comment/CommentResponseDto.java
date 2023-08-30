package com.sparta.seoulmate.dto.comment;

import com.sparta.seoulmate.dto.ApiResponseDto;
import com.sparta.seoulmate.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto extends ApiResponseDto {
    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
