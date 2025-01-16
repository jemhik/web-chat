package com.serhiikovtyka.webchat.controller;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetChatHistory() {
        List<ChatMessageDto> messageDtos = List.of(new ChatMessageDto(), new ChatMessageDto());

        when(messageService.getLast50Messages()).thenReturn(messageDtos);

        List<ChatMessageDto> result = messageController.getChatHistory();
        assertNotNull(result, "The message list should not be null");
        assertEquals(2, result.size(), "The message list size should be 2");
        assertEquals(messageDtos, result, "The message list should match the expected list");
    }
}
