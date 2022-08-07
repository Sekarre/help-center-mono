package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.EventNotification;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class EventNotificationMapper {

    public abstract EventNotificationDTO mapEventNotificationToEventNotificationDTO(EventNotification eventNotification);
}
