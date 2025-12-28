package com.modufit.facility.dto;

import com.modufit.facility.entity.Facility;
import com.modufit.facility.entity.FavoriteFacility;
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

    public static FacilitySummaryDto from(Facility facility) {
        return FacilitySummaryDto.builder()
                .facilityId(facility.getFacilityId())
                .facilityName(facility.getFacilityName())
                .facilityType(facility.getFacilityType())
                .build();
    }
}
