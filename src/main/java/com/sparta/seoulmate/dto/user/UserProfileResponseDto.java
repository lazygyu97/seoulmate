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
public class UserProfileResponseDto {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String phone;
    private Integer age;
    private String city;
    private String district;
    private String address;
    private String image;


    public static UserProfileResponseDto of(User user) {
        return UserProfileResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .age(user.getAge())
                .city(user.getCity())
                .district(user.getDistrict())
                .address(user.getAddress())
                .image(user.getImage())
                .build();
    }
}