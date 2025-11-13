package com.modufit.chat.service;

import com.modufit.chat.dto.ChatResponseDto;

public interface ChatParticipantService {
    ChatResponseDto enterChatRoom(Long chatRoomId, Long userId);
    ChatResponseDto leaveChatRoom(Long chatRoomId, Long userId);
}
