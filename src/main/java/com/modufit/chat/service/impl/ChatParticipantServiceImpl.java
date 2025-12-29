package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.enums.MessageType;
import com.modufit.chat.entity.ChatMessage;
import com.modufit.chat.entity.ChatParticipant;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatMessageRepository;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.service.ChatParticipantService;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.ParticipantExceptions;
import com.modufit.users.entity.User;
import com.modufit.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    @Transactional
    public ChatResponseDto enterChatRoom(Long chatRoomId, Long userId) {
        ChatRoom room = chatRoomService.getChatRoomById(chatRoomId);
        User user = userService.getUserWithProfile(userId);

        ChatParticipant participant = checkChatParticipant(room, user);

        return createFromParams(
                MessageType.ENTER,
                room,
                user,
                user.getUserProfile().getUserName() + "님이 입장하셨습니다."
        );
    }

    private ChatParticipant checkChatParticipant(ChatRoom room, User user) {
        Optional<ChatParticipant> existing = chatParticipantRepository
                .findByChatRoom_RoomIdAndUser_UserId(room.getRoomId(), user.getUserId());

        if (existing.isPresent()) {
            ChatParticipant participant = existing.get();
            if (!participant.getIsActive()) {
                participant.setIsActive(true);
                participant.setJoinedAt(LocalDateTime.now());
                return chatParticipantRepository.save(participant);
            }
            return participant;
        }

        ChatParticipant participant = ChatParticipant.create(room, user);
        return chatParticipantRepository.save(participant);
    }

    @Override
    @Transactional
    public ChatResponseDto leaveChatRoom(Long chatRoomId, Long userId) {
        ChatRoom room = chatRoomService.getChatRoomById(chatRoomId);
        User user = userService.getUserWithProfile(userId);

        ChatParticipant participant = chatParticipantRepository
                .findByChatRoom_RoomIdAndUser_UserId(chatRoomId, userId)
                .orElseThrow(() -> new ParticipantExceptions.ParticipantNotFoundException(chatRoomId, userId));

        participant.setIsActive(false);
        chatParticipantRepository.save(participant);

        return createFromParams(
                MessageType.LEAVE,
                room,
                user,
                user.getUserProfile().getUserName() + "님이 퇴장하셨습니다."
        );
    }

    @Override
    public void leftAtParticipant(Long chatRoomId, Long participantId) {
        ChatParticipant participant = chatParticipantRepository.findByChatRoom_RoomIdAndParticipantId(chatRoomId, participantId)
                .orElseThrow(() -> new ParticipantExceptions.ParticipantNotFoundException(chatRoomId, participantId));

        ChatMessage message = chatMessageRepository.findTopByChatRoom_RoomIdOrderBySentAtDesc(chatRoomId);
        participant.updateLeftAt(LocalDateTime.now(), message.getMessageId());

        chatParticipantRepository.save(participant);
    }

    private ChatResponseDto createFromParams(MessageType type, ChatRoom room, User user, String message) {
        return ChatResponseDto.builder().
                messageType(type)
                .chatRoomId(room.getRoomId())
                .senderId(user.getUserId())
                .senderName(user.getUserProfile().getUserName())
                .message(message)
                .sendTime(LocalDateTime.now())
                .build();
    }
}
