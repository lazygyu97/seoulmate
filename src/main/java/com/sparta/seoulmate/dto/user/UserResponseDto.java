package com.sparta.seoulmate.dto.user;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String address;
    private List<String> interests;
    private String image;

    public static UserResponseDto of(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .address(user.getAddress())
                .image(Optional.ofNullable(user.getImage())
                        .map(Image::getImageUrl) // 이미지가 있으면 URL 추출, 없으면 "default"
                        .orElse("default"))
                .interests(Optional.ofNullable(user.getUserInterests())
                        .map(interests -> interests.stream()
                                .map(userInterest -> userInterest.getInterests().getTitle())
                                .collect(Collectors.toList())) // 관심사가 있으면 제목 리스트로 매핑, 없으면 null
                        .orElse(null))
                .build();

    }
}
