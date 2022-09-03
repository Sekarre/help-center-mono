package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.chat.ChatMessageDTO;
import com.sekarre.chatdemo.domain.ChatMessage;
import org.mapstruct.*;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class ChatMessageMapper {

    @Mapping(target = "senderId", source = "chatMessage.sender.id")
    @Mapping(target = "senderName", source = "chatMessage.sender.firstName")
    @Mapping(target = "senderLastname", source = "chatMessage.sender.lastName")
    public abstract ChatMessageDTO mapMessageToChatMessageDTO(ChatMessage chatMessage);

    public abstract ChatMessage mapChatMessageDTOToMessage(ChatMessageDTO chatMessageDTO);
}
