package com.sekarre.chatdemo.exceptions;

public class ChatAuthorizationException extends RuntimeException{

    public ChatAuthorizationException(String message) {
        super(message);
    }
}
