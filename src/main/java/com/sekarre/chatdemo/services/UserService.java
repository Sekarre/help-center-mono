package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.UserDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsersByRoleName(String roleName);

    User getUserById(Long userId);

    List<UserDTO> getParticipantsByIssue(Issue issue);

    User getAvailableSupportUser();
}
