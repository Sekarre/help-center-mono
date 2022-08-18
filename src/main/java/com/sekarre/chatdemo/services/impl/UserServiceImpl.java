package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.UserDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.Role;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.domain.enums.RoleName;
import com.sekarre.chatdemo.exceptions.user.UserNotFoundException;
import com.sekarre.chatdemo.exceptions.user.RoleNotFoundException;
import com.sekarre.chatdemo.mappers.UserMapper;
import com.sekarre.chatdemo.repositories.RoleRepository;
import com.sekarre.chatdemo.repositories.UserRepository;
import com.sekarre.chatdemo.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getUsersByRoleName(String roleName) {
        Role role = getRoleByName(roleName);
        return userRepository.findAllByRolesContaining(role).stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
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

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(Enum.valueOf(RoleName.class, roleName))
                .orElseThrow(() -> new RoleNotFoundException("Role with name: " + roleName + " not found"));
    }
}
