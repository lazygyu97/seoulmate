package com.sparta.seoulmate.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAddressRequestDto {
    private String address;
    private String password;
}
