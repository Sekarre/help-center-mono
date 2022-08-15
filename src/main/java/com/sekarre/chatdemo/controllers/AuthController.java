package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.auth.UserCredentials;
import com.sekarre.chatdemo.services.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sekarre.chatdemo.controllers.AuthController.BASE_AUTH_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_AUTH_URL)
public class AuthController {

    public static final String BASE_AUTH_URL = "/api/v1/auth";
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserCredentials userCredentials) {
        return ResponseEntity.ok(authService.getToken(userCredentials));
    }
}
