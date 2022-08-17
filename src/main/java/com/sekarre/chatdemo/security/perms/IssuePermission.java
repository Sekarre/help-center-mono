package com.sekarre.chatdemo.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@PreAuthorize("hasRole('ADMIN') or (hasRole('SUPPORT') and @issueAuthorizationServiceImpl.checkIfUserAuthorizedToIssue(#issueId))")
public @interface IssuePermission {
}
