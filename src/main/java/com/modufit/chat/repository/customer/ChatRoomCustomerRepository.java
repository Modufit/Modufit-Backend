package com.modufit.chat.repository.customer;

import com.modufit.chat.dto.ChatRoomResponseDto;
import java.util.List;

public interface ChatRoomCustomerRepository {
    List<ChatRoomResponseDto> findChatRooms(long userId);
}
