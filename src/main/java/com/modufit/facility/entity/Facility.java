package com.modufit.facility.entity;

import com.modufit.chat.entity.ChatRoom;
import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facilities", indexes = {
        @Index(name = "idx_region", columnList = "region"),
        @Index(name = "idx_sport_type", columnList = "sport_type"),
        @Index(name = "idx_facility_name", columnList = "facility_name"),
        @Index(name = "idx_location", columnList = "latitude, longitude")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long facilityId;

    @Column(name = "facility_name", nullable = false, length = 200)
    private String facilityName;

    @Column(name = "facility_type", nullable = false, length = 100)
    private String facilityType;

    @Column(name = "sport_type", nullable = false, length = 100)
    private String sportType;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(name = "open_weekday", nullable = false)
    private LocalTime openWeekday;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @OneToOne(mappedBy = "facility", cascade = CascadeType.ALL)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FacilitySchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FacilityProgram> programs = new ArrayList<>();
}