package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotification;
import com.sekarre.chatdemo.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification, Long> {

    List<EventNotification> findAllByUserIdAndReadIsFalse(Long userId);

    List<EventNotification> findAllByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);

    Optional<EventNotification> findFirstByDestinationIdAndUserIdAndEventType(String destinationId, Long userId, EventType eventType);
}
