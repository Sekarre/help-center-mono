package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotificationLimiter;
import com.sekarre.chatdemo.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EventNotificationLimiterRepository extends JpaRepository<EventNotificationLimiter, Long> {

    boolean existsByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);

    @Transactional
    void deleteByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);
}
