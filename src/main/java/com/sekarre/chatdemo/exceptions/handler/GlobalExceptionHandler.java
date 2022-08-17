package com.sekarre.chatdemo.exceptions.handler;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleChatNotFoundException(AppRuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    private ErrorMessage getCustomErrorMessage(String e) {
        return ErrorMessage.builder()
                .cause(e)
                .timestamp(DateUtil.getCurrentDateTime())
                .build();
    }
}
