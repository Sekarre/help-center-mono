package com.sekarre.chatdemo.services.user;

import com.sekarre.chatdemo.DTO.user.UserDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.exceptions.user.UserNotFoundException;
import com.sekarre.chatdemo.mappers.UserMapper;
import com.sekarre.chatdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getUsersByRoleName(String roleName) {
        return userRepository.findAllByRoleName(roleName).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByRoleNameAndNotInIssue(String roleName, Long issueId) {
        return userRepository.findAllByRoleNameAndIssueIdNotEqual(roleName, issueId).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersByIds(Long[] usersIds) {
        List<User> users = new ArrayList<>(usersIds.length);
        for (Long userId : usersIds) {
            users.add(getUserById(userId));
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    public List<UserDTO> getParticipantsByIssue(Issue issue) {
        return issue.getParticipants().stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public User getAvailableSupportUser() {
        return userRepository.findUsersWithLeastIssuesAndMatchingSpecialization()
                .orElseThrow(() -> new IllegalStateException("Cannot find available users"));
    }
}
