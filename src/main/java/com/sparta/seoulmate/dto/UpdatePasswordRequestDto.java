package com.sparta.seoulmate.dto;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.redishash.Password;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    private String updatePassword;

    public Password toEntity(User user, String encodedPassword) {
        Password updatePassword = Password.builder()
                .memberId(user.getId())
                .updatedPassword(encodedPassword)
                .build();
        return updatePassword;
    }
}
