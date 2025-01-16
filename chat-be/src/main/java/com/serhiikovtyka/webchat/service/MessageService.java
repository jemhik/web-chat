package com.serhiikovtyka.webchat.service;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;

import java.util.List;

public interface MessageService {

    ChatMessageDto processMessage(ChatMessageDto message);

    List<ChatMessageDto> getLast50Messages();
}
