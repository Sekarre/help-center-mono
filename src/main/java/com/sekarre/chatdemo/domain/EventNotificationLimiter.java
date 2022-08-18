package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.EventType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationLimiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long userId;
}
