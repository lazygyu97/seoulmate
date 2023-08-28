package com.sparta.seoulmate.dto.comment;

import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {
    private String content;

    public Comment toEntity(Post post, User user) {
        Comment comment = Comment.builder()
                .content(this.content)
                .post(post)
                .author(user)
                .build();
        return comment;
    }
}