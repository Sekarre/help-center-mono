package com.sekarre.chatdemo.exceptions;

public class WebSocketAuthenticationException extends RuntimeException{

    public WebSocketAuthenticationException(String message) {
        super(message);
    }
}
