package com.sekarre.chatdemo.exceptions;

public abstract class AppRuntimeException extends RuntimeException {

    public AppRuntimeException(String message) {
        super(message);
    }
}
