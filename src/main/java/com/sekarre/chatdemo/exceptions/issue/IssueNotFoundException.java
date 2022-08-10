package com.sekarre.chatdemo.exceptions.issue;

public class IssueNotFoundException extends GenericIssueException{
    public IssueNotFoundException(String message) {
        super(message);
    }
}
