package com.sekarre.chatdemo.exceptions.issue;

public class IllegalIssueStatusException extends GenericIssueException {
    public IllegalIssueStatusException(String message) {
        super(message);
    }
}
