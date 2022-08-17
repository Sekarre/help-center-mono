package com.sekarre.chatdemo.exceptions.issue;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public abstract class AppIssueException extends AppRuntimeException {
    public AppIssueException(String message) {
        super(message);
    }
}
