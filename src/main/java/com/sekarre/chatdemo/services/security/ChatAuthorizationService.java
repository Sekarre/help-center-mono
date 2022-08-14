package com.sekarre.chatdemo.services.security;

import com.sekarre.chatdemo.domain.User;

public interface ChatAuthorizationService {

    boolean checkIfUserAuthorizedToJoinChannel(String channelId);
    boolean checkIfUserAuthorizedToJoinChannel(User user, String channelId);
}
