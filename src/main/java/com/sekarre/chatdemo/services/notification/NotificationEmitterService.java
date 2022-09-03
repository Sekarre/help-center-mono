package com.sekarre.chatdemo.services.notification;

import com.sekarre.chatdemo.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    void sendNewEventMessage(EventType eventType, String destinationId, Long[] usersId);
}
