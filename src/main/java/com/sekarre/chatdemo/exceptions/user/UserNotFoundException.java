package com.sekarre.chatdemo.exceptions.user;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public class UserNotFoundException extends AppRuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
