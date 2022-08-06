package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.domain.enums.SseEventType;
import com.sekarre.chatdemo.exceptions.EmitterEventSendException;
import com.sekarre.chatdemo.services.EventEmitterService;
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

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    @Override
    public void removeEmitter() {
        emitterMap.remove(getCurrentUser().getId());
    }

    @Override
    public SseEmitter createNewEmitter() {
        Long userId = getCurrentUser().getId();
        if (emitterMap.containsKey(userId)) {
            return emitterMap.get(userId);
        }
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> {
            log.debug("Emitter with id: " + userId + " successfully finished task");
            removeEmitter();
        });
        sseEmitter.onTimeout(() -> {
            log.debug("Emitter with uuid: " + userId + " couldn't finish task before timeout");
            removeEmitter();
        });
        emitterMap.put(userId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void sendNewEmitterMessage(SseEventType sseEventType, String payload) {
        Long userId = getCurrentUser().getId();
        try {
            emitterMap.get(userId).send(SseEmitter.event().reconnectTime(500).name(sseEventType.name()).data(payload));
        } catch (IOException e) {
            throw new EmitterEventSendException("Emitter send event failed for id: " + userId + " and event: " + sseEventType);
        }
    }

    @Override
    public void sendNewEmitterMessage(SseEventType sseEventType, String payload, Long[] usersId) {
        for (Long userId : usersId) {
            try {
                emitterMap.get(userId).send(SseEmitter.event().name(sseEventType.name()).data(payload).build());
            } catch (IOException e) {
                throw new EmitterEventSendException("Emitter send event failed for id: " + userId + " and event: " + sseEventType);
            }
        }
    }
}

