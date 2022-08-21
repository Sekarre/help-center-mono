package com.sekarre.chatdemo.exceptions.comment;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;

public class CommentException extends AppRuntimeException {
    public CommentException(String message) {
        super(message);
    }
}
