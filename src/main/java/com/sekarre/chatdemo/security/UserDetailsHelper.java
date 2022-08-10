package com.sekarre.chatdemo.security;

import com.sekarre.chatdemo.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsHelper {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserFullName() {
        return getCurrentUser().getName() + " " + getCurrentUser().getLastname();
    }
}