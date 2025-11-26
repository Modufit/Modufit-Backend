package com.modufit.chat.repository.customer;

import com.modufit.chat.dto.ChatRoomListItemDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomCustomerRepository {
    List<ChatRoomListItemDto> findChatRooms(long userId, Pageable pageable);
}
