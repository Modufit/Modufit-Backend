package com.modufit.chat.repository.customer;

import com.modufit.chat.dto.ChatRoomListItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomCustomerRepository {
    Page<ChatRoomListItemDto> findChatRooms(long userId, Pageable pageable);
}
