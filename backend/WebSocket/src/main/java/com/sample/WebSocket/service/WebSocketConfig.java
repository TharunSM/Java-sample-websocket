package com.sample.WebSocket.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
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
//                .addInterceptors(new AuthHandshakeInterceptor()) //interceptor is not able to find the header
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}