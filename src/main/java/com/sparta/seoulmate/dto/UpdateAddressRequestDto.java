package com.sparta.seoulmate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAddressRequestDto {
    private String city;
    private String district;
    private String address;
    private String password;
}
