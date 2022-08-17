package com.sekarre.chatdemo.exceptions.chat;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public abstract class ChatException extends AppRuntimeException {
    public ChatException(String message) {
        super(message);
    }
}
