package com.serhiikovtyka.webchat.service.impl;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.entity.ChatMessage;
import com.serhiikovtyka.webchat.mapper.ChatMessageMapper;
import com.serhiikovtyka.webchat.repository.ChatMessageRepository;
import com.serhiikovtyka.webchat.service.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ChatMessageRepository messageRepository; // Assume JPA repository

    ChatMessageMapper mapper = ChatMessageMapper.INSTANCE;

    @Override
    @Transactional
    public ChatMessageDto processMessage(ChatMessageDto message) {
        ChatMessage entity = mapper.toEntity(message);
        return mapper.toDto(messageRepository.save(entity));
    }

    @Override
    public List<ChatMessageDto> getLast50Messages() {
        List<ChatMessage> messages = messageRepository.findTop50ByOrderByTimestampAsc();
        return messages.stream().map(mapper::toDto).toList();
    }

}
