package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto extends ApiResponseDto{ // 왜?????
    private Long id;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
