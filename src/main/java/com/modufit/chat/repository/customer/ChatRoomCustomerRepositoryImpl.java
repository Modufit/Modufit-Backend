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
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomCustomerRepositoryImpl implements ChatRoomCustomerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ChatRoomListItemDto> findChatRooms(long userId, Pageable pageable) {
        QChatParticipant p = QChatParticipant.chatParticipant;
        QChatRoom room = QChatRoom.chatRoom;
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

        List<ChatRoomListItemDto> content = jpaQueryFactory
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
                ).fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(p.count())
                .from(p)
                .where(p.user.userId.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
