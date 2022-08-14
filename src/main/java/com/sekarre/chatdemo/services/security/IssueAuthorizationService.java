package com.sekarre.chatdemo.services.security;

public interface IssueAuthorizationService {

    boolean checkIfUserAuthorizedToIssue(Long issueId);

    boolean checkIfUserAuthorizedToIssueComment(Long issueId);
}
