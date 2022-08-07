package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.User;

public interface UserAuthorizationService {
    boolean checkIfUserIsAuthorizedToJoinChannel(String channelId);
    boolean checkIfUserIsAuthorizedToJoinChannel(User user, String channelId);
}
