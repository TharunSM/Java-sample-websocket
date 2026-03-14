package com.sample.WebSocket.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enables a simple memory-based message broker to carry messages
        // back to the client on destinations prefixed with /topic
        config.enableSimpleBroker("/topic");

        // Designates the /app prefix for messages that are bound for methods
        // annotated with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint where clients connect to the WebSocket server
        registry.addEndpoint("/connect/message")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    //authenticate header
    //this interruption check dosent happen for all the chat message, only during initialization it happens
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // STOMP headers are "native" headers
                    String authToken = accessor.getFirstNativeHeader("Authorization");

                    if (!"ThisIsASampleSocketProgram!".equals(authToken)) {
                        throw new IllegalArgumentException("Unauthenticated!");
                    }
                }
                return message;
            }
        });
    }
}