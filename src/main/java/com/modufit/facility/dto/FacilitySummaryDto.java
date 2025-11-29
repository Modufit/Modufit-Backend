package com.modufit.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilitySummaryDto {
    private Long facilityId;
    private String facilityName;
    private String facilityType;
}
