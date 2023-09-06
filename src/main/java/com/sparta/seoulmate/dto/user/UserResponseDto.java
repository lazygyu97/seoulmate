package com.sparta.seoulmate.dto.user;

import com.sparta.seoulmate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String image;

    public static UserResponseDto of(User user) {

        if(user.getImage()==null){
            return UserResponseDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .image("default")
                    .build();
        }else {
            return UserResponseDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .image(user.getImage().getImageUrl())
                    .build();
        }

    }
}
