package com.sekarre.chatdemo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String channelId;

    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> chatMessages;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminUser_id")
    private User adminUser;

    @ManyToMany
    @JoinTable(
            name = "chat_has_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
}
