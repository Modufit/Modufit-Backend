package com.modufit.chat.service;

import com.modufit.chat.dto.ChatRequestDto;
import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.enums.MessageType;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageService {
    ChatResponseDto saveMessage(ChatRequestDto messageDto);
    List<ChatResponseDto> getMessagesAfter(Long chatRoomId, LocalDateTime afterTime, int limit);
    Long getMessageCount(Long chatRoomId);
}
