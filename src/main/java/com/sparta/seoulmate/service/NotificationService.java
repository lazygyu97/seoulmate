package com.sparta.seoulmate.service;

import java.io.IOException;
import java.util.Map;

import com.sparta.seoulmate.dto.notification.NotificationListResponseDto;
import com.sparta.seoulmate.dto.notification.NotificationRequestDto;
import com.sparta.seoulmate.dto.notification.NotificationResponseDto;
import com.sparta.seoulmate.entity.*;
import com.sparta.seoulmate.repository.EmitterRepository;
import com.sparta.seoulmate.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // SSE 유효시간 : 1시간
    public static final String CLIENT_BASIC_URL = "https://localhost:8080/api";
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    // SSE 연결 수행
    @Transactional
    public SseEmitter subscribe(User user, String lastEventId) {
        String emitterId = makeTimeIncludeId(user);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 수행 완료나 시간 만료 시 사용이 끝난 emitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(user);
        sendToClient(emitter, emitterId, eventId,
                user.getId() + "번 사용자에게 연결되었습니다.");

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithUserId(
                    String.valueOf(user.getId()));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getKey(),
                            entry.getValue()));
        }
        return emitter;
    }

    private String makeTimeIncludeId(User user) {  // 데이터 유실 시점 파악 위함
        return user.getId() + "_" + System.currentTimeMillis();
    }

    // 특정 SseEmitter 를 이용해 알림을 보냅니다. SseEmitter 는 최초 연결 시 생성되며,
    // 해당 SseEmitter 를 생성한 클라이언트로 알림을 발송하게 됩니다.
    public void sendToClient(SseEmitter emitter, String emitterId, String eventId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name("sse")
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            throw new IllegalArgumentException("존재하지 않는 알림입니다.");
        }
    }

    // 알람 보내기 (notificationRepository 에 저장 후 보내기)
    public void send(NotificationRequestDto requestDto) {
        sendNotification(requestDto, saveNotification(requestDto));
    }

    // 알람 저장
    @Transactional
    public Notification saveNotification(NotificationRequestDto requestDto) {
        Notification notification = Notification.builder()
                .receiver(requestDto.getReceiver())
                .content(requestDto.getContent())
                .build();
        notificationRepository.save(notification);
        return notification;
    }

    //     알림 보내기
    public void sendNotification(NotificationRequestDto request, Notification notification) {
        String receiverId = String.valueOf(request.getReceiver().getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();

        // 유저의 모든 SseEmitter 가져옴
        Map<String, SseEmitter> emitters = emitterRepository
                .findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장 (유실된 데이터 처리 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, eventId, NotificationResponseDto.of(notification));
                    System.out.println(receiverId + "데이터 전송 완료");
                }
        );
    }


    // 게시글 좋아요 버튼 클릭 시 전송되는 알림
    @Transactional
    public void postLikeNotification(PostLike postLike) {
        User receiver = postLike.getPost().getAuthor(); // 게시글 작성자
        String content =
                postLike.getUser().getUsername() + "님이 회원님의 ["
                        + postLike.getPost().getTitle() + "] 게시글을 좋아합니다.";

        NotificationRequestDto requestDto = NotificationRequestDto.builder()
                .content(content)
                .receiver(receiver)
                .build();

        send(requestDto);
    }

    // 게시글에 댓글을 남길 시 전송되는 알림
    @Transactional
    public void commentNotification(Comment comment) {
        User receiver = comment.getPost().getAuthor(); // 게시글 작성자
        String content =
                comment.getAuthor().getUsername() + "님이 ["
                        + comment.getPost().getTitle() + "] 에 댓글을 남겼습니다: "
                        + comment.getContent();

        NotificationRequestDto requestDto = NotificationRequestDto.builder()
                .content(content)
                .receiver(receiver)
                .build();

        send(requestDto);
    }

    //  사용자 follow 시 전송되는 알림
    @Transactional
    public void followNotification(Follow follow) {
        User receiver = follow.getFollowingUser(); // 팔로우 받은 사람
        String content =
                follow.getUser().getUsername() + "님이 당신을 팔로우 합니다.";

        NotificationRequestDto requestDto = NotificationRequestDto.builder()
                .content(content)
                .receiver(receiver)
                .build();

        send(requestDto);
    }

    // 알림 목록 조회
    @Transactional(readOnly = true)
    public NotificationListResponseDto getNotifications(User user) {
        return NotificationListResponseDto.of(
                notificationRepository.findAllByReceiverId(user.getId()));
    }
}