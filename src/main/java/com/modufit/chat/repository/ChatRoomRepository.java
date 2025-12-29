package com.modufit.chat.repository;

import com.modufit.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.facility WHERE cr.roomId = :chatRoomId")
    Optional<ChatRoom> findWithFacility(@Param("chatRoomId") Long chatRoomId);
}
