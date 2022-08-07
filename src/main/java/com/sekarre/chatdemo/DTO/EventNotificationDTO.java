package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.domain.enums.SseEventType;
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
    private String channelId;
    private SseEventType sseEventType;

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
