package com.modufit.chat.service;

import com.modufit.chat.dto.ChatRoomDetailResponseDto;
import com.modufit.chat.dto.PageChatRoomResponseDto;

public interface ChatRoomService {
    void createChatRoom(Long facilityId);
    PageChatRoomResponseDto getUserChatRooms(Long userId, int page, int size);
    ChatRoomDetailResponseDto getChatRoomDetail(Long chatRoomId);
}
