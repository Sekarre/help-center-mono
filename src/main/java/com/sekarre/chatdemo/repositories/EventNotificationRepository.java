package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventNotificationRepository extends JpaRepository<EventNotification, Long> {
}
