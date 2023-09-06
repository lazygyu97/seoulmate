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
    private String address;
    @Positive
    private String age;
    @NotNull
    String gender;
    @Email
    @NotBlank
    private String email;

    @Builder.Default
    private boolean admin = false;
    @Builder.Default
    private String adminToken = "";

    UserGenderEnum genderEnum;

    @Builder
    public User toEntity(UserRoleEnum role,String password) {
        if(gender=="남성"){
            this.genderEnum=UserGenderEnum.MALE;
        }else {
            this.genderEnum=UserGenderEnum.FEMALE;
        }
        return User.builder()
                .username(this.username)
                .password(password)
                .nickname(this.nickname)
                .phone(this.phone)
                .address(this.address)
                .age(Integer.valueOf(this.age))
                .gender(this.genderEnum)
                .email(this.email)
                .role(role)
                .build();
    }
}
