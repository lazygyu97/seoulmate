package com.sparta.seoulmate.repository.chat;

import com.sparta.seoulmate.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    void deleteByUuid(String uuid);
    Optional<ChatRoom> findByUuid(String uuid);
}
