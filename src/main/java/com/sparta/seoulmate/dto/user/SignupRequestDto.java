package com.sparta.seoulmate.dto.user;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserGenderEnum;
import com.sparta.seoulmate.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive
    private Integer age;

    @NotNull
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
                .username(this.username)
                .password(password)
                .nickname(this.nickname)
                .phone(this.phone)
                .city(this.city)
                .district(this.district)
                .address(this.address)
                .age(this.age)
                .gender(this.gender)
                .email(this.email)
                .role(role)
                .build();
    }
}
