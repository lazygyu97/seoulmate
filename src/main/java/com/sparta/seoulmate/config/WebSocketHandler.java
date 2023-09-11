package com.sparta.seoulmate.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.seoulmate.dto.chat.MessageDto;
import com.sparta.seoulmate.dto.chat.RoomDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.chat.ChatMessage;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import com.sparta.seoulmate.repository.UserRepository;
import com.sparta.seoulmate.repository.chat.ChatMessageRepository;
import com.sparta.seoulmate.repository.chat.ChatRoomRepository;
import com.sparta.seoulmate.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    // 각 채팅방 별로 WebSocket 세션을 저장하는 맵
    private Map<String, Set<WebSocketSession>> roomSessionsMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // URI에서 uuid 파라미터 값을 추출
        String roomId = extractRoomIdFromSession(session);

        // 현재 세션을 해당 채팅방의 세션 목록에 추가
        roomSessionsMap
                .computeIfAbsent(roomId, id -> ConcurrentHashMap.newKeySet())
                .add(session);
    }

    private String extractRoomIdFromSession(WebSocketSession session) {
        // URI에서 uuid 파라미터 값을 추출하는 로직을 작성
        // 예: ws://localhost:8080/ws/chat?uuid=sample-uuid 여기에서 'sample-uuid'를 추출
        URI sessionUri = session.getUri();
        if (sessionUri == null) return null;

        String query = sessionUri.getQuery();
        if (query == null || !query.startsWith("uuid=")) return null;

        return query.split("uuid=")[1];
    }

    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);
        String roomId = messageDto.getRoomId();
        log.info("session {}", messageDto);

        User user = userRepository.findByUsername(messageDto.getSender()).get();

        ChatRoom chatRoom = chatRoomRepository.findByUuid(roomId).get();
        log.info("room {}", chatRoom);

        ChatMessage chatMessage = messageDto.toEntity(user, chatRoom);
        chatMessageRepository.save(chatMessage);

        // 모든 해당 채팅방의 사용자에게 메시지 전송
        for (WebSocketSession roomSession : roomSessionsMap.get(roomId)) {
            roomSession.sendMessage(message);
        }

        // message의 유형에 따라 handleAction 호출
        RoomDto roomDto = new RoomDto(roomId, chatRoom.getRoomName()); // RoomDto 생성
        roomDto.handleAction(session, messageDto, chatService);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 연결이 종료되면 모든 채팅방에서 해당 세션을 제거
        roomSessionsMap.values().forEach(sessions -> sessions.remove(session));
    }
}
