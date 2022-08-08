package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotificationLimiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EventNotificationLimiterRepository extends JpaRepository<EventNotificationLimiter, Long> {

    boolean existsByChannelIdAndUserId(String channelId, Long userId);

    @Transactional
    void deleteByChannelIdAndUserId(String channelId, Long userId);
}
