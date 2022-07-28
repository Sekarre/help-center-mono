package com.sekarre.chatdemo.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @Lob
    @Basic
    private byte[] file;

    @JoinColumn(name = "sender_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User sender;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
