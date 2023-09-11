package com.sparta.seoulmate.dto.chat;

import com.sparta.seoulmate.dto.comment.CommentListResponseDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.chat.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListResponseDto {
    private List<ChatRoomResponseDto> chatRoomList;

    public static ChatRoomListResponseDto of (List<ChatRoom> chatRooms){
        List<ChatRoomResponseDto> chatRoomResponseDto = chatRooms.stream().map(ChatRoomResponseDto::of).toList();
        return ChatRoomListResponseDto.builder()
                .chatRoomList(chatRoomResponseDto)
                .build();
    }
}