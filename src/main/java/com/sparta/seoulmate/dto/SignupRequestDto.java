package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserGenderEnum;
import com.sparta.seoulmate.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.awt.geom.Area;

@Builder
@Getter
public class SignupRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String phone;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String address;
    @NotBlank
    private int age;

    @NotBlank
    UserGenderEnum gender;

    @Email
    @NotBlank
    private String email;

    @Builder.Default
    private boolean admin = false;
    @Builder.Default
    private String adminToken = "";


    @Builder
    public User toEntity(UserRoleEnum role,String password) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .phone(phone)
                .city(city)
                .district(district)
                .address(address)
                .gender(gender)
                .email(email)
                .role(role)
                .build();
    }
}
