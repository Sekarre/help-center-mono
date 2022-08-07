package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.SseEventType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String channelId;

    private SseEventType sseEventType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    private boolean read = false;
}
