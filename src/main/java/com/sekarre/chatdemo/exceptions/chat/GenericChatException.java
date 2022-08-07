package com.sekarre.chatdemo.exceptions.chat;

public abstract class GenericChatException extends RuntimeException{
    public GenericChatException(String message) {
        super(message);
    }
}
