package com.modufit.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDetailResponseDto {
    private FacilityDetailDto facilityDetail;
    private Boolean facilityLiked;
}
