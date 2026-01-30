package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "facility_programs",
        indexes = {
                @Index(name = "idx_program_work", columnList = "program_work"),
                @Index(name = "idx_program_price", columnList = "program_price")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityProgram extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(name = "program_operating_day", length = 100)
    private String programOperatingDay;

    @Column(name = "program_operating_time", length = 100)
    private String programOperatingTime;

    @Column(name = "program_work", length = 100)
    private String programWork;

    @Column(name = "program_target", length = 200)
    private String programTarget;

    @Column(name = "program_max_participants")
    private Integer programMaxParticipants;

    @Column(name = "program_min_participants")
    private Integer programMinParticipants;

    @Column(name = "program_current_participants")
    private Integer programCurrentParticipants;

    @Column(name = "program_price")
    private BigDecimal programPrice;

    @Column(name = "program_reservation")
    private Boolean programReservation;

    @Column(name = "program_image")
    private String programImage;
}

