package com.modufit.facility.entity;

import com.modufit.common.BaseTimeEntity;
import com.modufit.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "favorite_facilities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_facility",
                        columnNames = {"user_id", "facility_id"}
                )
        },
        indexes = {
                @Index(name = "idx_user", columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FavoriteFacility extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favoriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    public static FavoriteFacility of(User user, Facility facility) {
        return FavoriteFacility.builder()
                .user(user)
                .facility(facility)
                .build();
    }

}