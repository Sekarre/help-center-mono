package com.sekarre.chatdemo.exceptions.roles;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
