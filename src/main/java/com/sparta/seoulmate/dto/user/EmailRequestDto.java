package com.sparta.seoulmate.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailRequestDto {
    private String email;

    public EmailRequestDto(String email) {
        this.email = email;
    }
}