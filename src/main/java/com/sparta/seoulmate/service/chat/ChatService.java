package com.sparta.seoulmate.service.chat;

import com.sparta.seoulmate.dto.chat.ChatRoomListResponseDto;
import com.sparta.seoulmate.dto.chat.ChatRoomResponseDto;
import com.sparta.seoulmate.dto.chat.RoomDto;
import com.sparta.seoulmate.entity.User;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {

    RoomDto createChatRoom(String name, User user);

    ChatRoomResponseDto getChatRoom(String uuid);

    ChatRoomListResponseDto getChatRooms();

    <T> void sendMessage(WebSocketSession session, T message);

    void deleteChatRoom(String uuid, User user);
}
