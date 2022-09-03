package com.sekarre.chatdemo.controllers.sse;

import com.sekarre.chatdemo.services.notification.NotificationEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.sekarre.chatdemo.controllers.sse.NotificationEmitterController.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_EMITTER_URL)
public class NotificationEmitterController {

    public static final String BASE_EMITTER_URL = "/api/v1/sse";

    private final NotificationEmitterService notificationEmitterService;

    @GetMapping("/events")
    public SseEmitter listenToEvents() {
        return notificationEmitterService.createNewEmitter();
    }
}
