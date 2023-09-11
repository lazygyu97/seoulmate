package com.sparta.seoulmate.dto.chat;

import com.sparta.seoulmate.entity.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private Long messageId;
    private String message;
    private String sender;
    private String time;

    public static ChatMessageResponseDto of (ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .messageId(chatMessage.getId())
                .sender(chatMessage.getUser().getUsername())
                .message(chatMessage.getMessage())
                .time(chatMessage.getTime().toString())
                .build();
    }
}
