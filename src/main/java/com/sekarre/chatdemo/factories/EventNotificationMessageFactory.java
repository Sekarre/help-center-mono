package com.sekarre.chatdemo.factories;

import com.sekarre.chatdemo.domain.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventNotificationMessageFactory {

    public String getEventNotificationMessage(EventType eventType) {
        switch (eventType) {
            case NEW_CHAT_MESSAGE -> {
                return "New chat message";
            }
            case REMOVED_FROM_CHAT -> {
                return "You have been removed from chat";
            }
            default -> {
                return "New notification";
            }
        }
    }
}
