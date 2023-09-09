package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findByUser_userId(long userId);
//    List<Notification> findAllByUser_userIdAndIsReadOrderByCreatedTimeDesc(Long userId, boolean isRead);

    List<Notification> findAllByReceiverId(Long id);
}
