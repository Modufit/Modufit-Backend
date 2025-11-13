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
public class ChatRoomResponseDto {
    public Long roomId;
    private String roomName;
    private Integer currentParticipants;
    private Integer maxParticipants;
    private Long unreadMessages;
    private String message;
    private LocalDateTime sentTime;
}
