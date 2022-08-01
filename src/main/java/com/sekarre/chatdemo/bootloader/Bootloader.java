package com.sekarre.chatdemo.bootloader;

import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.repositories.ChatRepository;
import com.sekarre.chatdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Bootloader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        createUsers();
        createDefaultChats();
    }

    private void createUsers() {
        userRepository.save(User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1"))
                .name("Adam")
                .lastname("Kowalski")
                .build());

        userRepository.save(User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test2"))
                .name("Aneta")
                .lastname("Nowak")
                .build());


        userRepository.save(User.builder()
                .username("test3")
                .password(passwordEncoder.encode("test3"))
                .name("Mateusz")
                .lastname("Lewandowski")
                .build());
    }

    private void createDefaultChats() {
        chatRepository.save(Chat.builder().id(1L).channelId("Test1").channelName("T1").build());
        chatRepository.save(Chat.builder().id(2L).channelId("Test2").channelName("T2").build());
        chatRepository.save(Chat.builder().id(3L).channelId("Test3").channelName("T3").build());
        Chat chat = chatRepository.save(Chat.builder().id(4L).channelId("Test4").channelName("T4").build());
        addUserToChat(chat);
    }

    private void addUserToChat(Chat chat) {
        List<User> users = userRepository.findAll();
        users.forEach(chat::addUser);
        chatRepository.save(chat);
    }
}
