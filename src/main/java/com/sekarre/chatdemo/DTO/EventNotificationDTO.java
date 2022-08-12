package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationDTO {

    private Long id;
    private String message;
    private String destinationId;
    private EventType eventType;

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
