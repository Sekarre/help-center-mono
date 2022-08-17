package com.sekarre.chatdemo.bootloader;

import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.IssueType;
import com.sekarre.chatdemo.domain.Role;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.domain.enums.IssueTypeName;
import com.sekarre.chatdemo.domain.enums.RoleName;
import com.sekarre.chatdemo.repositories.ChatRepository;
import com.sekarre.chatdemo.repositories.IssueTypeRepository;
import com.sekarre.chatdemo.repositories.RoleRepository;
import com.sekarre.chatdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Bootloader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoles();
        createNormalUsers();
        createSupportUsers();
        createAdminUser();
        createDefaultChats();
        createDefaultIssueTypes();
    }

    private void createRoles() {
        roleRepository.save(Role.builder()
                .name(RoleName.BASIC)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.ADMIN)
                .build());
    }

    private void createNormalUsers() {
        Role role = roleRepository.findByName(RoleName.BASIC).get();

        userRepository.save(User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1"))
                .roles(Collections.singleton(role))
                .firstName("Adam")
                .lastName("Kowalski")
                .build());

        userRepository.save(User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test2"))
                .roles(Collections.singleton(role))
                .firstName("Aneta")
                .lastName("Nowak")
                .build());


        userRepository.save(User.builder()
                .username("test3")
                .password(passwordEncoder.encode("test3"))
                .roles(Collections.singleton(role))
                .firstName("Mateusz")
                .lastName("Lewandowski")
                .build());
    }

    private void createSupportUsers() {
        Role role = roleRepository.findByName(RoleName.SUPPORT).get();

        userRepository.save(User.builder()
                .username("sup1")
                .password(passwordEncoder.encode("sup1"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_1")
                .build());

        userRepository.save(User.builder()
                .username("sup2")
                .password(passwordEncoder.encode("sup2"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_2")
                .build());


        userRepository.save(User.builder()
                .username("sup3")
                .password(passwordEncoder.encode("sup3"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_3")
                .build());

    }

    private void createAdminUser() {
        Role role = roleRepository.findByName(RoleName.ADMIN).get();

        userRepository.save(User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_ADMIN")
                .build());
    }

    private void createDefaultIssueTypes() {
        issueTypeRepository.save(IssueType.builder().name(IssueTypeName.GAME_ISSUE).build());
        issueTypeRepository.save(IssueType.builder().name(IssueTypeName.ACCOUNT_ISSUE).build());
        issueTypeRepository.save(IssueType.builder().name(IssueTypeName.SOCIAL).build());
        issueTypeRepository.save(IssueType.builder().name(IssueTypeName.OTHER).build());
        issueTypeRepository.save(IssueType.builder().name(IssueTypeName.GAME_CLIENT_ISSUE).build());
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
