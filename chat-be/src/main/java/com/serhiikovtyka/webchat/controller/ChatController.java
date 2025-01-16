package com.serhiikovtyka.webchat.controller;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.entity.ChatMessage;
import com.serhiikovtyka.webchat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(ChatMessageDto chatMessage) {
        return messageService.processMessage(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(ChatMessageDto chatMessage) {
        chatMessage.setContent(chatMessage.getSenderName() + " joined");
        return chatMessage;
    }
}
