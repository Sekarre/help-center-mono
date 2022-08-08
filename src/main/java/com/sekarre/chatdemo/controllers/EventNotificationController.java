package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.services.EventNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/event-notification")
public class EventNotificationController {

    private final EventNotificationService eventNotificationService;

    @GetMapping
    public List<EventNotificationDTO> getRemainingNotifications() {
        return eventNotificationService.getAllRemainingNotifications();
    }

    @PatchMapping("/{channelId}")
    public void markEventNotificationAsRead(@PathVariable String channelId) {
        eventNotificationService.markNotificationAsRead(channelId);
    }
}
