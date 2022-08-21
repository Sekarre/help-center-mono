package com.sekarre.chatdemo.exceptions.comment;

public class CommentNotFoundException extends CommentException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
