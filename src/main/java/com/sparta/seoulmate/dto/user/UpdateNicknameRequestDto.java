package com.sparta.seoulmate.dto.user;

import com.sparta.seoulmate.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNicknameRequestDto {
    private String nickname;
    private String password;
}
