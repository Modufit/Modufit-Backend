package com.modufit.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatParticipantListItemDto {
    private Long participantId;
    private Long userId;
    private String userName;
    private String sportType;
}
