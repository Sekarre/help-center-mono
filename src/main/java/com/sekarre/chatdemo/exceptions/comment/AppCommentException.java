package com.sekarre.chatdemo.exceptions.comment;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public class AppCommentException extends AppRuntimeException {
    public AppCommentException(String message) {
        super(message);
    }
}
