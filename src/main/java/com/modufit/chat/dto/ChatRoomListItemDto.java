package com.modufit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListItemDto {
    public Long roomId;
    private String roomName;
    private Long unreadMessagesCount;
    private String message;
    private LocalDateTime sentTime;
    private Boolean isActive;
}
