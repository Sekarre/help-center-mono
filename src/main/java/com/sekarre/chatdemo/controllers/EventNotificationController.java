package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.security.perms.EventNotificationPermission;
import com.sekarre.chatdemo.services.EventNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/event-notifications")
public class EventNotificationController {

    private final EventNotificationService eventNotificationService;

    @GetMapping
    public List<EventNotificationDTO> getUnreadNotifications() {
        return eventNotificationService.getAllUnreadNotifications();
    }

    @EventNotificationPermission
    @PatchMapping("/{destinationId}")
    public void markEventNotificationAsRead(@PathVariable String destinationId, @RequestParam String eventType) {
        eventNotificationService.markNotificationAsRead(destinationId, Enum.valueOf(EventType.class, eventType));
    }

    @GetMapping("/{destinationId}/count")
    public Integer getEventNotificationCount(@PathVariable String destinationId, @RequestParam String eventType) {
        return eventNotificationService.getNotificationCount(destinationId, Enum.valueOf(EventType.class, eventType));
    }
}
