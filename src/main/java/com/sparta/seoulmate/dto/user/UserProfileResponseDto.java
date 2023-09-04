package com.sparta.seoulmate.dto.user;

import com.sparta.seoulmate.entity.Image;
import com.sparta.seoulmate.entity.SeoulApi;
import com.sparta.seoulmate.entity.SeoulApiLike;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.openApi.dto.ItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<ItemResponseDto> seoulApiLikes;


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
                .image(user.getImage().getImageUrl())
                .seoulApiLikes(user.getSeoulApiLikes().stream().map(seoulApiLike -> ItemResponseDto.of(seoulApiLike.getSeoulApi())).toList())
                .build();
    }
}