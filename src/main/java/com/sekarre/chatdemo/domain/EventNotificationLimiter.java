package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.EventType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private EventType eventType;
    private Long userId;
}
