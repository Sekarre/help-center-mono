package com.sekarre.chatdemo.DTO.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.domain.enums.EventType;
import lombok.*;

import java.time.LocalDateTime;

import static com.sekarre.chatdemo.util.DateUtil.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private String message;
    private String destinationId;
    private EventType eventType;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
