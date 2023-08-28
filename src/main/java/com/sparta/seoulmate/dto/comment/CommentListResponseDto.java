package com.sparta.seoulmate.dto.comment;

import com.sparta.seoulmate.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {
    private List<CommentResponseDto> commentList;
    public static CommentListResponseDto of (List<Comment> commensts){
        List<CommentResponseDto> commentResponseDto = commensts.stream().map(CommentResponseDto::of).toList();
        return CommentListResponseDto.builder()
                .commentList(commentResponseDto)
                .build();
    }
}
