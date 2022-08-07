package com.sekarre.chatdemo.exceptions;

public class ChatNotFoundException extends GenericChatException {

    public ChatNotFoundException(String message) {
        super(message);
    }
}
