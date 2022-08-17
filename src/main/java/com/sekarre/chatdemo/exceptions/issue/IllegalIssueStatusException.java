package com.sekarre.chatdemo.exceptions.issue;

public class IllegalIssueStatusException extends AppIssueException {
    public IllegalIssueStatusException(String message) {
        super(message);
    }
}
