package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatIdOrderByCreatedDateTime(Long chatId);
}
