package com.modufit.chat.dto;

import com.modufit.facility.dto.FacilitySummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailResponseDto {
    private Long roomId;
    private String roomName;
    private FacilitySummaryDto facilitySummary;
    private Integer participantCount;
    private List<ChatParticipantListItemDto> participants;
}
