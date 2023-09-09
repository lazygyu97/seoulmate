package com.sparta.seoulmate.repository.chat;

import com.sparta.seoulmate.dto.chat.ChatRoomDto;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    void deleteByUuid(String uuid);
    Optional<ChatRoomDto> findByUuid(String uuid);
}
