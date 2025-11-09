package com.modufit.users.entity;

import com.modufit.common.BaseTimeEntity;
import com.modufit.users.entity.enums.AgeGroup;
import com.modufit.users.entity.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profiles", indexes = {
        @Index(name = "idx_region", columnList = "region"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(name = "sport_type", length = 100)
    private String sportType;

    // 개인 회원 전용 필드
    @Enumerated(EnumType.STRING)
    @Column(name = "age_group")
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    // 단체 회원 전용 필드
    @Column(name = "member_count")
    private Integer memberCount;
}