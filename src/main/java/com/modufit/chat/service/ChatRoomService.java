package com.modufit.chat.service;

import com.modufit.chat.dto.ChatRoomDetailResponseDto;
import com.modufit.chat.dto.PageChatRoomResponseDto;
import com.modufit.chat.entity.ChatRoom;

public interface ChatRoomService {
    ChatRoom getChatRoomById(Long roomId);
    void createChatRoom(Long facilityId);
    PageChatRoomResponseDto getUserChatRooms(Long userId, int page, int size);
    ChatRoomDetailResponseDto getChatRoomDetail(Long chatRoomId);
}
