package com.sekarre.chatdemo.exceptions.user;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public class RoleNotFoundException extends AppRuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
