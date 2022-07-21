package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.User;

public interface UserAuthorizationService {
    void checkIfUserIsAuthorizedToJoinChannel(String channelId);
    void checkIfUserIsAuthorizedToJoinChannel(User user, String channelId);
}
