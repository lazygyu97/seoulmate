package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserInterest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InterestListResponseDto {
    private List<InterestResponseDto> userInterestList;

    public static InterestListResponseDto of(List<UserInterest> userInterests) {
        List<InterestResponseDto> userInterest = userInterests.stream().map(InterestResponseDto::of).toList();
        return InterestListResponseDto.builder()
                .userInterestList(userInterest)
                .build();
    }
}
