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

    @Column(name = "progrm_ty_nm", length = 200)
    private String programType;

    @Column(name = "progrm_nm", length = 200, nullable = false)
    private String programName;

    @Column(name = "progrm_trget_nm", length = 200)
    private String programTarget;

    @Column(name = "progrm_begin_de")
    private LocalDate beginDate;

    @Column(name = "progrm_end_de")
    private LocalDate endDate;

    @Column(name = "progrm_estbl_wkday_nm", length = 200)
    private String openWeekday;

    @Column(name = "progrm_estbl_tizn_value", length = 200)
    private String timeZoneValue;

    @Column(name = "progrm_rcrit_nmpr_co", precision = 38, scale = 0)
    private BigDecimal recruitCapacity;

    @Column(name = "progrm_prc", precision = 28, scale = 5)
    private BigDecimal programPrice;

}
