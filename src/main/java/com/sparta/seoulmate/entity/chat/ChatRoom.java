package com.sparta.seoulmate.entity.chat;

import com.sparta.seoulmate.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "ChatRooms")
public class ChatRoom {
    @Id
    private String uuid;

    @Column
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
