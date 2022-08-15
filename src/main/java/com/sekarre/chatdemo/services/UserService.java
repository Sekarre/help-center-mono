package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.UserDTO;
import com.sekarre.chatdemo.domain.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers(String roleName);

    User getUserById(Long userId);
}
