package com.sekarre.chatdemo.exceptions.comment;

public class CommentNotFoundException extends GenericCommentException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
