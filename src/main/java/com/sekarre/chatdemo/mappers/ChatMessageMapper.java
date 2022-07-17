package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.domain.ChatMessage;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class ChatMessageMapper {

    @Mapping(target = "senderId", source = "chatMessage.sender.id")
    @Mapping(target = "senderName", source = "chatMessage.sender.name")
    public abstract ChatMessageDTO mapMessageToChatMessageDTO(ChatMessage chatMessage);

    public abstract ChatMessage mapChatMessageDTOToMessage(ChatMessageDTO chatMessageDTO);
}
