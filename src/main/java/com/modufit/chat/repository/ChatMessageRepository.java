package com.modufit.chat.repository;

import com.modufit.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom_RoomIdAndSentAtAfterOrderBySentAtAsc(
            Long chatRoomId,
            LocalDateTime afterTime
    );
    Long countByChatRoom_RoomId(Long chatRoomId);
    void deleteBySender_UserId(Long senderId);
    void deleteByChatRoom_RoomId(Long chatRoomId);

    @Query("SELECT COUNT(m) FROM ChatMessage m " +
            "WHERE m.chatRoom.roomId = :roomId " +
            "AND m.messageId > :lastReadMessageId")
    Long countUnreadMessages(@Param("roomId") Long roomId,
                             @Param("lastReadMessageId") Long lastReadMessageId);

    ChatMessage findTopByChatRoom_RoomIdOrderBySentAtDesc(Long chatRoomId);
}
