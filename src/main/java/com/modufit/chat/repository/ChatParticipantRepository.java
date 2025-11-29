package com.modufit.chat.repository;

import com.modufit.chat.dto.ChatParticipantListItemDto;
import com.modufit.chat.entity.ChatParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends CrudRepository<ChatParticipant, Long> {
    Optional<ChatParticipant> findByChatRoom_RoomIdAndUser_UserId(Long chatRoomId, Long userId);
    Optional<ChatParticipant> findByChatRoom_RoomIdAndParticipantId(Long chatRoomId, Long participantId);

    Long countByUser_UserId(Long userId);

    @Query("""
    SELECT new com.modufit.chat.dto.ChatParticipantListItemDto(
        cp.participantId,
        u.userId,
        up.userName,
        up.sportType
    )
    FROM ChatParticipant cp
    JOIN cp.user u
    JOIN u.userProfile up
    WHERE cp.chatRoom.roomId = :roomId
""")
    List<ChatParticipantListItemDto> findChatUsers(@Param("roomId") Long roomId);

}
