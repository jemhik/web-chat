package com.serhiikovtyka.webchat.service;

import com.serhiikovtyka.webchat.controller.MessageController;
import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.entity.ChatMessage;
import com.serhiikovtyka.webchat.mapper.ChatMessageMapper;
import com.serhiikovtyka.webchat.repository.ChatMessageRepository;
import com.serhiikovtyka.webchat.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class MessageServiceTest {

    @Mock
    private ChatMessageRepository messageRepository;

    @Mock
    private ChatMessageMapper mapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessMessage() {
        ChatMessageDto messageDto = new ChatMessageDto();
        ChatMessage messageEntity = new ChatMessage();

        when(mapper.toEntity(messageDto)).thenReturn(messageEntity);
        when(messageRepository.save(messageEntity)).thenReturn(messageEntity);
        when(mapper.toDto(messageEntity)).thenReturn(messageDto);

        ChatMessageDto result = messageService.processMessage(messageDto);
        assertNotNull(result, "The processed message should not be null");
        assertEquals(messageDto, result, "The processed message should match the input message");
    }

    @Test
    public void testGetLast50Messages() {
        List<ChatMessage> messages = List.of(new ChatMessage(), new ChatMessage());
        List<ChatMessageDto> messageDtos = List.of(new ChatMessageDto(), new ChatMessageDto());

        when(messageRepository.findTop50ByOrderByTimestampAsc()).thenReturn(messages);
        when(mapper.toDto(messages.get(0))).thenReturn(messageDtos.get(0));
        when(mapper.toDto(messages.get(1))).thenReturn(messageDtos.get(1));

        List<ChatMessageDto> result = messageService.getLast50Messages();
        assertNotNull(result, "The message list should not be null");
        assertEquals(2, result.size(), "The message list size should be 2");
        assertEquals(messageDtos, result, "The message list should match the expected list");
    }
}
