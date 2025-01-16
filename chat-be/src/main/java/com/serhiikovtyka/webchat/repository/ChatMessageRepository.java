package com.serhiikovtyka.webchat.repository;

import com.serhiikovtyka.webchat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop50ByOrderByTimestampAsc();
}
