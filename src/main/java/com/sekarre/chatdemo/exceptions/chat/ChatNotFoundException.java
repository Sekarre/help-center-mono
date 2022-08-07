package com.sekarre.chatdemo.exceptions.chat;

public class ChatNotFoundException extends GenericChatException {

    public ChatNotFoundException(String message) {
        super(message);
    }
}
