package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.notification.NotificationDTO;
import com.sekarre.chatdemo.domain.Notification;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class NotificationMapper {

    public abstract NotificationDTO mapEventNotificationToEventNotificationDTO(Notification notification);
}
