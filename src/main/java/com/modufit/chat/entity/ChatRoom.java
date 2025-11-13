package com.modufit.chat.entity;

import com.modufit.common.BaseTimeEntity;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.entity.FacilitySchedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @OneToOne
    @JoinColumn(name = "schedule_id", unique = true, nullable = false)
    private FacilitySchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(name = "room_name", nullable = false, length = 200)
    private String roomName;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatMessage> messages = new ArrayList<>();

    public static ChatRoom of(FacilitySchedule schedule, Facility facility, String roomName) {
        return ChatRoom.builder()
                .schedule(schedule)
                .facility(facility)
                .roomName(roomName)
                .build();
    }
}
