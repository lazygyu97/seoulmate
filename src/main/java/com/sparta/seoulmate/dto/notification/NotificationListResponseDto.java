package com.sparta.seoulmate.dto.notification;

import com.sparta.seoulmate.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NotificationListResponseDto {
    private List<NotificationResponseDto> chatList;

    public static NotificationListResponseDto of(List<Notification> notifications) {
        return NotificationListResponseDto.builder()
                .chatList(notifications.stream().map(NotificationResponseDto::of)
                        .toList())
                .build();
    }
}
