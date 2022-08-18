package com.sekarre.chatdemo.security;

import com.sekarre.chatdemo.domain.Role;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.domain.enums.RoleName;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDetailsHelper {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserFullName() {
        return getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName();
    }

    public static boolean checkForRole(RoleName roleName) {
        return getCurrentUser().getRoles().stream()
                .map(Role::getName)
                .anyMatch(rn -> rn.equals(roleName));
    }
}