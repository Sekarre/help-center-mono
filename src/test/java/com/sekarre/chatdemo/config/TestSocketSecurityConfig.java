package com.sekarre.chatdemo.config;

import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.Ordered;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Order(Ordered.HIGHEST_PRECEDENCE)
@TestConfiguration
public class TestSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer  {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/chat-app/**", "/room/**", "/websocket/**").permitAll()
                .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
                .simpSubscribeDestMatchers("/room/**", "/chat-app/**", "/websocket/**").permitAll()
                .anyMessage().permitAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
