package com.sekarre.chatdemo.exceptions.handler;

import com.sekarre.chatdemo.exceptions.ChatNotFoundException;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ChatNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleChatNotFoundException(ChatNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<CustomErrorMessage> handleAuthenticationException(BadCredentialsException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    private CustomErrorMessage getCustomErrorMessage(String e) {
        return CustomErrorMessage.builder()
                .cause(e)
                .timestamp(DateUtil.getCurrentDateTime())
                .build();
    }
}
