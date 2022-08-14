package com.sekarre.chatdemo.services.security;


import com.sekarre.chatdemo.DTO.auth.TokenResponse;
import com.sekarre.chatdemo.DTO.auth.UserCredentials;

public interface AuthService {

    TokenResponse getToken(UserCredentials userCredentials);
}