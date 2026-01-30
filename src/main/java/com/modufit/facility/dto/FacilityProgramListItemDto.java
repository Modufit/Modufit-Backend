package com.modufit.facility.dto;

import com.modufit.facility.entity.FacilityProgram;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityProgramListItemDto {
    private Long programId;
    private String programType;
    private String programName;
    private String programTarget;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String openWeekday;
    private String timeZoneValue;
    private BigDecimal recruitCapacity;
    private BigDecimal programPrice;
    private Boolean isParticipating;

}
