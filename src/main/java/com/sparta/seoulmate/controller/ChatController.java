package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.chat.ChatRoomDto;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<ChatRoomDto> createChatRoom(@RequestParam(name = "room-name") String roomName, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatRoomDto chatRoomDto = chatService.createChatRoom(roomName, userDetails.getUser());
        return ResponseEntity.ok().body(chatRoomDto);
    }

    @GetMapping("/chat")
    public List<ChatRoomDto> findAllRoom() {
        return chatService.findAllRoom();
    }
}
