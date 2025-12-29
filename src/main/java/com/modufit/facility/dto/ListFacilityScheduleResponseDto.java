package com.modufit.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListFacilityScheduleResponseDto {
    private Long scheduleCount;
    private List<FacilityScheduleListItemDto> facilitySchedules;
}
