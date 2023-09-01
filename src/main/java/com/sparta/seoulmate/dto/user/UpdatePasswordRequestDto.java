package com.sparta.seoulmate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    private String password;
    private String updatePassword;

    public UpdatePasswordRequestDto updatePassword(String updatePassword) {
        return UpdatePasswordRequestDto.builder()
                .password(this.password)
                .updatePassword(updatePassword)
                .build();
    }
}
