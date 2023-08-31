package com.sparta.seoulmate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    private String updatePassword;

    public void updatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
    }
}
