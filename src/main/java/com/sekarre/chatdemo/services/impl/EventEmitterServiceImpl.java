package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.services.EventEmitterService;
import com.sekarre.chatdemo.services.EventNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.sekarre.chatdemo.security.UserDetailsHelper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventEmitterServiceImpl implements EventEmitterService {

    public static final long TIMEOUT = 7_200_000L; //2hours
    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private final EventNotificationService eventNotificationService;

    @Override
    public void removeEmitter(Long userId) {
        emitterMap.remove(userId);
    }

    @Override
    public SseEmitter createNewEmitter() {
        Long userId = getCurrentUser().getId();
        if (emitterMap.containsKey(userId)) {
            return emitterMap.get(userId);
        }
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        sseEmitter.onCompletion(() -> {
            log.debug("Emitter with id: " + userId + " successfully finished task");
            removeEmitter(userId);
        });
        sseEmitter.onTimeout(() -> {
            log.debug("Emitter with uuid: " + userId + " couldn't finish task before timeout");
            sseEmitter.complete();
            removeEmitter(userId);
        });
        emitterMap.put(userId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void sendNewEventMessage(EventType eventType, String channelId) {
        Long userId = getCurrentUser().getId();
        try {
            emitterMap.get(userId).send(SseEmitter.event().name(eventType.name()).data(channelId).reconnectTime(500).build());
        } catch (IOException e) {
            log.debug("Emitter send event failed for id: " + userId + " and event: " + eventType);
        }
    }

    @Override
    public void sendNewEventMessage(EventType eventType, String channelId, Long[] usersId) {
        for (Long userId : usersId) {
            try {
                if (!eventNotificationService.isNotificationStopped(channelId, userId, eventType)) {
                    if (emitterMap.containsKey(userId)) {
                        emitterMap.get(userId).send(SseEmitter.event().name(eventType.name()).data(channelId).reconnectTime(500).build());
                    }
                    eventNotificationService.saveEventNotification(eventType, channelId, userId);
                }
            } catch (IOException e) {
                log.debug("Emitter send event failed for id: " + userId + " and event: " + eventType);
            }
        }
    }
}

