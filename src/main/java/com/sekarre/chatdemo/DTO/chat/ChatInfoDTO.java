package com.sekarre.chatdemo.DTO.chat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatInfoDTO {

    private String channelName;
    private String channelId;
}
