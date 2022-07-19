package com.sekarre.chatdemo.config;

import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.repositories.UserRepository;
import com.sekarre.chatdemo.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/room");
        registry.setApplicationDestinationPrefixes("/chat-app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
                    log.debug("Header auth token: " + token);

                    User user = userRepository.findByUsername(jwtTokenUtil.getUsername(token))
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());

                    accessor.setUser(authentication);
                } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (Objects.nonNull(authentication))
                        log.info("Disconnected Auth : " + authentication.getName());
                    else
                        log.info("Disconnected Sess : " + accessor.getSessionId());
                }
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
                if (sha.getCommand() == null) {
                    log.warn("postSend null command");
                    return;
                }

                String sessionId = sha.getSessionId();

                switch (sha.getCommand()) {
                    case CONNECT -> log.info("STOMP Connect [sessionId: " + sessionId + "]");
                    case CONNECTED -> log.info("STOMP Connected [sessionId: " + sessionId + "]");
                    case DISCONNECT -> log.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                    default -> {
                    }
                }
            }
        });
    }
}