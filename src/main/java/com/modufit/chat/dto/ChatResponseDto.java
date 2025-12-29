package com.modufit.chat.dto;

import com.modufit.chat.dto.enums.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
    private MessageType messageType;
    private Long chatRoomId;
    private Long senderId;
    private String senderName;
    private String message;
    private LocalDateTime sendTime;
}