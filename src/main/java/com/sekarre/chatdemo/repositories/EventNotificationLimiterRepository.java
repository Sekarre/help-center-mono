package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotificationLimiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventNotificationLimiterRepository extends JpaRepository<EventNotificationLimiter, Long> {

    boolean existsByChannelIdAndUserId(String channelId, Long userId);

    void deleteByChannelIdAndUserId(String channelId, Long userId);
}
