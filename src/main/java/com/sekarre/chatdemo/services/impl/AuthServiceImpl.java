package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.auth.TokenResponse;
import com.sekarre.chatdemo.DTO.auth.UserCredentials;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.repositories.UserRepository;
import com.sekarre.chatdemo.security.JwtTokenUtil;
import com.sekarre.chatdemo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse getToken(UserCredentials userCredentials) {
        User user = (User) userDetailsService.loadUserByUsername(userCredentials.getUsername());
        if (passwordEncoder.matches(userCredentials.getPassword(), user.getPassword()))
            return new TokenResponse(jwtTokenUtil.generateAccessToken(user));
        throw new BadCredentialsException("Given credentials are invalid");
    }
}