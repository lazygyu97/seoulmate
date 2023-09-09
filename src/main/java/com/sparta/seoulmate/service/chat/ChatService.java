package com.sparta.seoulmate.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.seoulmate.dto.chat.ChatRoomDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import com.sparta.seoulmate.repository.chat.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDto> chatRoomDtoMap;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        chatRoomDtoMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        return new ArrayList<>(chatRoomDtoMap.values());
    }

    public ChatRoomDto findRoomById(String roomId) {
        return chatRoomDtoMap.get(roomId);
    }

    public ChatRoomDto createChatRoom(String roomName, User user) {
        String randomId = UUID.randomUUID().toString(); // 랜덤한 방 아이디 생성

        // Builder를 이용해서 ChatRoomDto 을 Building
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .uuid(randomId)
                .roomName(roomName)
                .build();
        chatRoomDtoMap.put(randomId, chatRoomDto); // 랜덤 아이디와 room 정보를 Map 에 저장

        ChatRoom chatRoom = chatRoomDto.toEntity(randomId, roomName, user);
        chatRoomRepository.save(chatRoom);
        return chatRoomDto;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
