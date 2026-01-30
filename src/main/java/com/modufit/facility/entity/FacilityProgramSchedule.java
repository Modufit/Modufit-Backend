package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import com.modufit.facility.entity.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "facility_schedules",
        indexes = {
                @Index(name = "idx_facility_date", columnList = "facility_id, reservation_date"),
                @Index(name = "idx_status", columnList = "status")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_facility_time",
                        columnNames = {"facility_id", "reservation_date", "start_time"})
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityProgramSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Column(name = "min_participants", nullable = false)
    @Builder.Default
    private Integer minParticipants = 1;

    @Column(name = "current_participants")
    @Builder.Default
    private Integer currentParticipants = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ScheduleStatus status = ScheduleStatus.AVAILABLE;

}