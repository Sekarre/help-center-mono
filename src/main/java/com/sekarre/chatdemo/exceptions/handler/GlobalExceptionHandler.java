package com.sekarre.chatdemo.exceptions.handler;

import com.sekarre.chatdemo.exceptions.ChatNotFoundException;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ChatNotFoundException.class)
    public ResponseEntity<CustomErrorMessage> handleChatAuthorizationException(ChatNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.ok(CustomErrorMessage.builder()
                .cause(e.getMessage())
                .timestamp(DateUtil.getCurrentDateTime())
                .build());
    }
}
