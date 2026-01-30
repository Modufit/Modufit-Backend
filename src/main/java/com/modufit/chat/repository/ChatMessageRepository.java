package com.modufit.chat.repository;

import com.modufit.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom_RoomIdAndSentAtAfterOrderBySentAtAsc(Long chatRoomId, LocalDateTime afterTime);
    ChatMessage findTopByChatRoom_RoomIdOrderBySentAtDesc(Long chatRoomId);

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chatRoom.roomId = :chatRoomId " +
            "ORDER BY m.messageId ASC")
    List<ChatMessage> getRecentMessages(
            @Param("chatRoomId") Long chatRoomId,
            Pageable pageable);

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chatRoom.roomId = :chatRoomId " +
            "AND m.messageId > :messageId " +
            "ORDER BY m.messageId ASC")
    List<ChatMessage> getMessagesBefore(
            @Param("chatRoomId") Long chatRoomId,
            @Param("messageId") Long messageId,
            Pageable pageable);

    void deleteBySender_UserId(Long senderId);
}
