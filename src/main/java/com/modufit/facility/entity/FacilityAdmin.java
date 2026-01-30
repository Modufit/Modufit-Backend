package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "facility_admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "facility_admin_email")
        },
        indexes = {
                @Index(name = "idx_facility_admin_email", columnList = "facility_admin_email")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityAdmin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_admin_id")
    private Long facilityAdminId;

    @Column(name = "facility_admin_email", nullable = false, length = 100)
    private String facilityAdminEmail;

    @Column(name = "facility_admin_password", nullable = false)
    private String facilityAdminPassword;

    @Column(name = "facility_admin_phone", length = 20)
    private String facilityAdminPhone;
}
