package com.serhiikovtyka.webchat.controller;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/history")
    public List<ChatMessageDto> getChatHistory() {
        return messageService.getLast50Messages();
    }
}
