package com.sekarre.chatdemo.services.notification;

import com.sekarre.chatdemo.domain.enums.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.sekarre.chatdemo.security.UserDetailsHelper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEmitterServiceImpl implements NotificationEmitterService {

    @Value("${notification.emitter.timeout:7200000}")
    private Long timeout;
    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private final NotificationService notificationService;

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
        SseEmitter sseEmitter = new SseEmitter(timeout);
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
    public void sendNewEventMessage(EventType eventType, String destinationId, Long[] usersId) {
        for (Long userId : usersId) {
            try {
                if (!notificationService.isNotificationStopped(destinationId, userId, eventType)) {
                    if (emitterMap.containsKey(userId)) {
                        emitterMap.get(userId).send(SseEmitter.event().name(eventType.name()).data(destinationId).build());
                    }
                    notificationService.saveEventNotification(eventType, destinationId, userId);
                }
            } catch (IOException e) {
                log.debug("Emitter send event failed for id: " + userId + " and event: " + eventType);
            }
        }
    }
}

