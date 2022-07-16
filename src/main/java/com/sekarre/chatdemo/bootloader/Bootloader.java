package com.sekarre.chatdemo.bootloader;

import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootloader implements CommandLineRunner {

    private final ChatRepository chatRepository;

    @Override
    public void run(String... args) throws Exception {
        chatRepository.save(Chat.builder().id(1L).channelId("Test1").build());
        chatRepository.save(Chat.builder().id(2L).channelId("Test2").build());
        chatRepository.save(Chat.builder().id(3L).channelId("Test3").build());
        chatRepository.save(Chat.builder().id(4L).channelId("Test4").build());
    }
}
