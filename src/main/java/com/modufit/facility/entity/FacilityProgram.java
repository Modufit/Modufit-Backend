package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "faility_programs")
@Getter
@Setter
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

    @Column(name = "program_type", length = 200)
    private String programType;

    @Column(name = "program_name", length = 200, nullable = false)
    private String programName;

    @Column(name = "program_target", length = 200)
    private String programTarget;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "open_weekday", length = 200)
    private String openWeekday;

    @Column(name = "time_zone_value", length = 200)
    private String timeZoneValue;

    @Column(name = "recruit_capacity", precision = 38)
    private BigDecimal recruitCapacity;

    @Column(name = "program_price", precision = 28, scale = 5)
    private BigDecimal programPrice;
}
