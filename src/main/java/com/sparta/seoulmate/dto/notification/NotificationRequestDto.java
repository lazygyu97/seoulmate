package com.sparta.seoulmate.dto.notification;

import com.sparta.seoulmate.entity.Notification;
import com.sparta.seoulmate.entity.User;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NotificationRequestDto {

    private String content;
    private User receiver;

    public Notification toEntity(User receiver) {
        return Notification.builder()
                .content(this.content)
                .receiver(receiver)
                .build();
    }
}
