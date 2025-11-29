package com.modufit.chat.repository.customer;

import com.modufit.chat.dto.ChatRoomListItemDto;
import com.modufit.chat.entity.QChatMessage;
import com.modufit.chat.entity.QChatParticipant;
import com.modufit.chat.entity.QChatRoom;
import com.modufit.facility.entity.QFacilitySchedule;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomCustomerRepositoryImpl implements ChatRoomCustomerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomListItemDto> findChatRooms(long userId, Pageable pageable) {
        QChatParticipant p = QChatParticipant.chatParticipant;
        QChatRoom room = QChatRoom.chatRoom;
        QFacilitySchedule schedule = QFacilitySchedule.facilitySchedule;
        QChatMessage message = QChatMessage.chatMessage;

        SubQueryExpression<Long> unreadCount = JPAExpressions
                .select(message.count())
                .from(message)
                .where(
                        message.chatRoom.roomId.eq(room.roomId)
                                .and(message.messageId.gt(p.lastReadMessageId))
                );

        SubQueryExpression<LocalDateTime> latestMessageTime = JPAExpressions
                .select(message.sentAt.max())
                .from(message)
                .where(message.chatRoom.roomId.eq(room.roomId));

        SubQueryExpression<String> latestMessageText = JPAExpressions
                .select(message.message)
                .from(message)
                .where(
                        message.chatRoom.roomId.eq(room.roomId)
                                .and(message.sentAt.eq(latestMessageTime))
                );

        return jpaQueryFactory
                .select(Projections.constructor(
                        ChatRoomListItemDto.class,
                        room.roomId,
                        room.roomName,
                        unreadCount,
                        latestMessageText,
                        latestMessageTime,
                        room.isActive
                ))
                .from(p)
                .join(p.chatRoom, room)
                .where(p.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        Expressions.dateTimeTemplate(LocalDateTime.class,
                                "GREATEST({0}, {1})",
                                p.lastVisitedAt,
                                Expressions.asDateTime(latestMessageTime)
                        ).desc()
                )
                .fetch();
    }

}
