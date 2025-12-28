package com.modufit.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDetailDto {
    private Long facilityId;
    private Long chatRoomId;
    private String facilityName;
    private String facilityType;
    private String sportType;
    private String address;
    private String openWeekday;
}
