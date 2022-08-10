package com.sekarre.chatdemo.exceptions.issue;

public abstract class GenericIssueException extends RuntimeException {
    public GenericIssueException(String message) {
        super(message);
    }
}
