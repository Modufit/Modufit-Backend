package com.modufit.facility.dto;

import com.modufit.facility.entity.FacilityProgramSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityScheduleListItemDto {
    private Long scheduleId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxParticipants;
    private Integer minParticipants;
    private Integer participants;
    private Boolean isParticipating;

    public static FacilityScheduleListItemDto from(FacilityProgramSchedule schedule) {
        return FacilityScheduleListItemDto.builder()
                .scheduleId(schedule.getScheduleId())
                .reservationDate(schedule.getReservationDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .maxParticipants(schedule.getMaxParticipants())
                .minParticipants(schedule.getMinParticipants())
                .participants(schedule.getCurrentParticipants())
                .build();
    }
}
