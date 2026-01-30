package com.modufit.users.entity;

import com.modufit.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_email")
        },
        indexes = {
                @Index(name = "idx_user_email", columnList = "user_email")
        }
)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "user_password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_gender", length = 10)
    private String userGender;

    @Column(name = "user_work", length = 50)
    private String userWork;

    @Column(name = "user_join_date", nullable = false)
    private LocalDate userJoinDate;

    @Column(name = "user_first_address", length = 20)
    private String firstAddress;     // 시/도

    @Column(name = "user_second_address", length = 20)
    private String secondAddress;    // 시/군/구

    @Column(name = "user_third_address", length = 20)
    private String thirdAddress;     // 읍/면/동

    @Column(name = "user_address_detail")
    private String addressDetail;

    @Column(name = "user_address_x")
    private Double addressX;         // 위도

    @Column(name = "user_address_y")
    private Double addressY;         // 경도

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
