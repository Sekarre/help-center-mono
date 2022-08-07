package com.sekarre.chatdemo.exceptions;

public abstract class GenericChatException extends RuntimeException{
    public GenericChatException(String message) {
        super(message);
    }
}
