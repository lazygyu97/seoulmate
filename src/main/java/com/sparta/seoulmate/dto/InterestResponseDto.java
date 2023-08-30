package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserInterest;
import com.sparta.seoulmate.entity.UserInterestEnum;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterestResponseDto {

    private Long id;
    private String userInterest;
    private Long userId;


    public static InterestResponseDto of(UserInterest userInterest) {
        return InterestResponseDto.builder()
                .id(userInterest.getId())
                .userInterest(userInterest.getInterests().getTitle())
                .userId(userInterest.getUser().getId())
                .build();
    }
}
