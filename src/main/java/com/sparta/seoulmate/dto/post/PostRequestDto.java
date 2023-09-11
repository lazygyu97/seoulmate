package com.sparta.seoulmate.dto.post;

import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private String address;

    public Post toEntity(User author) {
        Post post = Post.builder()
                .author(author)
                .title(this.title)
                .content(this.content)
                .address(author.getAddress())
                .build();
        return post;
    }
}
