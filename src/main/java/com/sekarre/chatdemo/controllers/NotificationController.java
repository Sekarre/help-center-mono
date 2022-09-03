package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.notification.NotificationDTO;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/event-notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getUnreadNotifications() {
        return notificationService.getAllUnreadNotifications();
    }

    @PatchMapping("/{destinationId}")
    public void markNotificationAsRead(@PathVariable String destinationId, @RequestParam String eventType) {
        notificationService.markNotificationAsRead(destinationId, Enum.valueOf(EventType.class, eventType));
    }

    @GetMapping("/{destinationId}/count")
    public Integer getNotificationCount(@PathVariable String destinationId, @RequestParam String eventType) {
        return notificationService.getNotificationCount(destinationId, Enum.valueOf(EventType.class, eventType));
    }
}
