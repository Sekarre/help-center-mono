package com.sekarre.chatdemo.services.user;

import com.sekarre.chatdemo.DTO.user.UserDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsersByRoleName(String roleName);

    List<UserDTO> getUsersByRoleNameAndNotInIssue(String roleName, Long issueId);

    List<User> getUsersByIds(Long[] usersIds);

    User getUserById(Long userId);

    List<UserDTO> getParticipantsByIssue(Issue issue);

    User getAvailableSupportUser();
}
