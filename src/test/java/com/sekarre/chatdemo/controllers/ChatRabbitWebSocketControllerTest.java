package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.config.ProfilesHolder;
import com.sekarre.chatdemo.config.TestSocketSecurityConfig;
import com.sekarre.chatdemo.services.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(ProfilesHolder.NO_AUTH)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSocketSecurityConfig.class)
class ChatRabbitWebSocketControllerTest {

    @LocalServerPort
    private int port;
    private String URL;

    private static final String SEND_MESSAGE_TO_CHAT_ENDPOINT = "/app/private-chat-room.";
    private static final String SUBSCRIBE_CHAT_ENDPOINT = "/topic/private.";

    private CompletableFuture<ChatMessageDTO> completableFuture;

    @Mock
    private ChatService chatService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/websocket";
    }

    @Test
    public void testSendMessageEndpoint() throws InterruptedException, ExecutionException, TimeoutException {
        String channelId = "Test1";
        ChatMessageDTO payloadChatMessageDTO = ChatMessageDTO.builder().message("Test").build();

        when(chatService.savePrivateChatMessage(any(ChatMessageDTO.class), any(String.class)))
                .thenReturn(payloadChatMessageDTO);

        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(10, SECONDS);

        stompSession.subscribe(SUBSCRIBE_CHAT_ENDPOINT + channelId, new CreateChatMessageStompFrameHandler());
        stompSession.send(SEND_MESSAGE_TO_CHAT_ENDPOINT + channelId, payloadChatMessageDTO);
        ChatMessageDTO receivedChatMessageDTO = completableFuture.get(10, SECONDS);

        assertNotNull(receivedChatMessageDTO);
        assertEquals(receivedChatMessageDTO.getMessage(), payloadChatMessageDTO.getMessage());
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class CreateChatMessageStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatMessageDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((ChatMessageDTO) o);
        }
    }
}