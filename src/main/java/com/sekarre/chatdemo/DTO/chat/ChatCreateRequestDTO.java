package com.sekarre.chatdemo.DTO.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class ChatCreateRequestDTO {

    private String channelName;
    private Long[] usersId;
}