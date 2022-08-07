package com.sekarre.chatdemo.exceptions.notification;

public abstract class GenericEventNotificationException extends RuntimeException {

    public GenericEventNotificationException(String message) {
        super(message);
    }
}
