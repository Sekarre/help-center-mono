package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.ChatInfoDTO;
import com.sekarre.chatdemo.domain.Chat;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class ChatMapper {

    public abstract ChatInfoDTO mapChatToChatInfoDTO(Chat chat);
}
