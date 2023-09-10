package com.sparta.seoulmate.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.seoulmate.dto.chat.ChatRoomListResponseDto;
import com.sparta.seoulmate.dto.chat.ChatRoomResponseDto;
import com.sparta.seoulmate.dto.chat.RoomDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import com.sparta.seoulmate.repository.chat.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private Map<String, RoomDto> chatRooms;

    @PostConstruct // 빈 생성과 의존성 주입이 완료된 후 호출되는 초기화 메소드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    @Override
    @Transactional
    @CacheEvict(value = "chatRooms", allEntries = true)
    public RoomDto createChatRoom(String name, User user) {
        String randomId = UUID.randomUUID().toString();
        RoomDto roomDto = RoomDto.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, roomDto);

        ChatRoom chatRoom = roomDto.toEntity(randomId, name, user);
        chatRoomRepository.save(chatRoom);
        return roomDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoomResponseDto getChatRoom(String uuid) {
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).get();
        return ChatRoomResponseDto.of(chatRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoomListResponseDto getChatRooms() {
        return ChatRoomListResponseDto.builder()
                .chatRoomList(chatRoomRepository.findAll().stream().map(ChatRoomResponseDto::of).toList())
                .build();

    }

    @Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "chatRooms", allEntries = true)
    public void deleteChatRoom(String uuid, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).get();
        if (!user.equals(chatRoom.getUser())) {
            throw new IllegalArgumentException("채팅방 관리자가 아닙니다.");
        }
        chatRoomRepository.deleteByUuid(uuid);
    }
}
