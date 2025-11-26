package com.modufit.chat.service;

import com.modufit.chat.dto.ChatRequestDto;
import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.enums.MessageType;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageService {
    ChatResponseDto saveMessage(ChatRequestDto messageDto);
    List<ChatResponseDto> getMessagesBefore(Long chatRoomId, Long lastMessageId, int limit);
    List<ChatResponseDto> getMessages(Long chatRoomId, int limit);
}
