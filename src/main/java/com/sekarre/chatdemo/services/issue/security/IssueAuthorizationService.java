package com.sekarre.chatdemo.services.issue.security;

public interface IssueAuthorizationService {

    boolean checkIfUserAuthorizedToIssue(Long issueId);

    boolean checkIfUserAuthorizedToIssueComment(Long issueId);
}
