package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "facilities",
        indexes = {
                @Index(name = "idx_facilities_name", columnList = "facility_name"),
                @Index(name = "idx_facilities_type", columnList = "facility_type"),
                @Index(name = "idx_facilities_sport_type", columnList = "sport_type"),
                @Index(name = "idx_facilities_address", columnList = "facility_first_address, facility_second_address")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long facilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_admin_id", nullable = false)
    private FacilityAdmin facilityAdmin;

    @Column(name = "facility_name", nullable = false, length = 200)
    private String facilityName;

    @Column(name = "facility_type", nullable = false, length = 100)
    private String facilityType;

    @Column(name = "sport_type", nullable = false, length = 100)
    private String sportType;

    @Column(name = "facility_image")
    private String facilityImage;

    @Column(name = "facility_url")
    private String facilityUrl;


    @Column(name = "facility_first_address", length = 20)
    private String firstAddress;     // 시/도

    @Column(name = "facility_second_address", length = 20)
    private String secondAddress;    // 시/군/구

    @Column(name = "facility_third_address", length = 20)
    private String thirdAddress;     // 읍/면/동

    @Column(name = "facility_address_detail")
    private String addressDetail;

    @Column(name = "facility_address_x")
    private Double addressX;         // 위도

    @Column(name = "facility_address_y")
    private Double addressY;         // 경도

    @Column(name = "facility_start_time")
    private LocalTime startTime;

    @Column(name = "facility_end_time")
    private LocalTime endTime;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FacilityProgram> programs = new ArrayList<>();
}
