package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.domain.EventNotification;
import com.sekarre.chatdemo.domain.enums.SseEventType;
import com.sekarre.chatdemo.factories.EventNotificationMessageFactory;
import com.sekarre.chatdemo.repositories.EventNotificationRepository;
import com.sekarre.chatdemo.services.EventNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventNotificationServiceImpl implements EventNotificationService {

    private final EventNotificationRepository eventNotificationRepository;

    @Override
    public void saveEventNotification(SseEventType sseEventType, String channelId) {
        eventNotificationRepository.save(EventNotification.builder()
                .message(new EventNotificationMessageFactory().getEventNotificationMessage(sseEventType))
                .channelId(channelId)
                .sseEventType(sseEventType)
                .build());
    }
}
