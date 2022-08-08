package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification, Long> {

    List<EventNotification> findAllByUserIdAndReadIsFalse(Long userId);

    List<EventNotification> findAllByChannelIdAndUserId(String channelId, Long userId);
}
