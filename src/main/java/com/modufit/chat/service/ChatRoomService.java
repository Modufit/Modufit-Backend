package com.modufit.chat.service;

import com.modufit.chat.dto.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomService {
    void createChatRoom(Long facilityId, Long scheduleId);
    void closedChatRoom(Long roomId, Long facilityId, Long scheduleId);

    List<ChatRoomResponseDto> getChatRooms(Long userId);
}
