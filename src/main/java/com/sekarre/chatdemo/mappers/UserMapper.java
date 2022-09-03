package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.user.UserDTO;
import com.sekarre.chatdemo.domain.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserDTO mapUserToUserDTO(User user);
}
