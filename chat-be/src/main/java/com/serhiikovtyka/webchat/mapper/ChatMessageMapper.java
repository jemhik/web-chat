package com.serhiikovtyka.webchat.mapper;

import com.serhiikovtyka.webchat.dto.ChatMessageDto;
import com.serhiikovtyka.webchat.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMessageMapper {

    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    ChatMessageDto toDto(ChatMessage chatMessage);

    ChatMessage toEntity(ChatMessageDto chatMessageDto);
}
