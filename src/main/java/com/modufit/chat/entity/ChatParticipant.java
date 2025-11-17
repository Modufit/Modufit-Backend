package com.modufit.chat.entity;

import com.modufit.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_participants",
        indexes = {
                @Index(name = "idx_room", columnList = "room_id"),
                @Index(name = "idx_user", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_room_user_active",
                        columnNames = {"room_id", "user_id", "is_active"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "last_visited_at")
    private LocalDateTime lastVisitedAt;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_read_message_id")
    @Builder.Default
    private Long lastReadMessageId = 0L;

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) {
            joinedAt = LocalDateTime.now();
        }
    }

    public void updateLeftAt(LocalDateTime lastVisitedAt, Long lastReadMessageId) {
        this.lastVisitedAt = lastVisitedAt;
        this.lastReadMessageId = lastReadMessageId;
    }

    public static ChatParticipant create(ChatRoom chatRoom, User user) {
        return ChatParticipant.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

    }
}
