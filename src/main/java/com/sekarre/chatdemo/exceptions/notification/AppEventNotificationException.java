package com.sekarre.chatdemo.exceptions.notification;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public abstract class AppEventNotificationException extends AppRuntimeException {

    public AppEventNotificationException(String message) {
        super(message);
    }
}
