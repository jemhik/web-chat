package com.serhiikovtyka.webchat.controller;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ChatControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        ChatMessageDto chatMessage = new ChatMessageDto();
        chatMessage.setContent("Hello");
        chatMessage.setSenderName("User");

        ChatMessageDto processedMessage = new ChatMessageDto();
        processedMessage.setContent("Hello");
        processedMessage.setSenderName("User");

        when(messageService.processMessage(chatMessage)).thenReturn(processedMessage);

        ChatMessageDto result = chatController.sendMessage(chatMessage);
        assertNotNull(result, "The processed message should not be null");
        assertEquals("Hello", result.getContent(), "The message content should be 'Hello'");
        assertEquals("User", result.getSenderName(), "The sender name should be 'User'");
    }

    @Test
    public void testAddUser() {
        ChatMessageDto chatMessage = new ChatMessageDto();
        chatMessage.setSenderName("User");

        ChatMessageDto result = chatController.addUser(chatMessage);
        assertNotNull(result, "The chat message should not be null");
        assertEquals("User joined", result.getContent(), "The message content should be 'User joined'");
        assertEquals("User", result.getSenderName(), "The sender name should be 'User'");
    }
}
