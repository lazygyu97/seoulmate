package com.sparta.seoulmate.dto.chat;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import com.sparta.seoulmate.service.chat.ChatService;
import lombok.Builder;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

public class ChatRoomDto {
    private String uuid; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDto(String uuid, String roomName){
        this.uuid = uuid;
        this.roomName = roomName;
    }

    public ChatRoom toEntity(String uuid, String roomName, User user) {
        return ChatRoom.builder()
                .uuid(uuid)
                .roomName(roomName)
                .user(user)
                .build();
    }

    public void handleActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
        if (chatMessageDto.getType().equals(ChatMessageDto.MessageType.ENTER)) {
            sessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장하셨습니다.");
            sendMessage(chatMessageDto, chatService);
        } else if (chatMessageDto.getType().equals(ChatMessageDto.MessageType.TALK)) {
            chatMessageDto.setMessage(chatMessageDto.getMessage());
            sendMessage(chatMessageDto, chatService);
        }
    }

        public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

}
