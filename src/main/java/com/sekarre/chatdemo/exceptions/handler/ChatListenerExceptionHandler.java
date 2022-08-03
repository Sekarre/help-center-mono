package com.sekarre.chatdemo.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ChatListenerExceptionHandler {

    @Around(value = "@annotation(ListenerErrorHandler)")
    public Object handleException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
