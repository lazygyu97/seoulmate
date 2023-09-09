package com.sparta.seoulmate.dto.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDto {
    public enum MessageType {
        ENTER, TALK
//      메세지 타입 : 입장, 대화
    }

    private MessageType type; // 메세지 타입
    private String roomId; // 채팅방 번호
    private String sender; // 메세지 보낸 사람
    private String message; // 메세지
    private String time; // 채팅 발송 시간
}