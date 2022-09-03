package com.sekarre.chatdemo.exceptions.handler;

import com.sekarre.chatdemo.exceptions.AppRuntimeException;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @MessageExceptionHandler(Throwable.class)
    @SendToUser(value = "/topic/private.errors")
    public ErrorMessage handleWebSocketException(Throwable e) {
        log.error(e.getMessage());
        return getCustomErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = AppRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleChatNotFoundException(AppRuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(getCustomErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
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
