package com.modufit.chat.repository;

import com.modufit.chat.entity.ChatParticipant;
import com.modufit.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends CrudRepository<ChatParticipant, Long> {
    Optional<ChatParticipant> findByChatRoom_RoomIdAndUser_UserId(Long chatRoomId, Long userId);
    Optional<ChatParticipant> findByChatRoom_RoomIdAndParticipantId(Long chatRoomId, Long participantId);

    Long countByUser_UserId(Long userId);
}
