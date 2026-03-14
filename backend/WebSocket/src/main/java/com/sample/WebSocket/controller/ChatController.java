package com.sample.WebSocket.controller;

import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/chat") // Receives messages from /app/chat
    @SendTo("/topic/messages") // Broadcasts result to /topic/messages
    public String handleMessage(@Payload String message,  @Headers Map<String, Object> headers) {
        System.out.println("Message recieved = "+ message);
        System.out.println("Headers: " + headers);
        return message;
    }
}