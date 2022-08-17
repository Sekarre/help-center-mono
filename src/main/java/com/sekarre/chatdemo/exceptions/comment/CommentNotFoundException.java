package com.sekarre.chatdemo.exceptions.comment;

public class CommentNotFoundException extends AppCommentException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
