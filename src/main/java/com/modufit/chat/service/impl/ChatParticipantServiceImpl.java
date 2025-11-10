package com.modufit.chat.service.impl;


import com.modufit.chat.dto.ChatRequestDto;
import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.enums.MessageType;
import com.modufit.chat.entity.ChatParticipant;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.service.ChatParticipantService;
import com.modufit.common.exception.business.ChatExceptions;
import com.modufit.common.exception.business.ParticipantExceptions;
import com.modufit.common.exception.business.UserExceptions;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ChatResponseDto enterChatRoom(Long chatRoomId, Long userId) {
        ChatRoom room = getChatRoom(chatRoomId);
        User user = getUser(userId);

        ChatParticipant participant = checkChatParticipant(chatRoomId, userId);

        return createFromParams(
                MessageType.ENTER,
                room,
                user,
                user.getUserProfile().getUserName() + "님이 입장하셨습니다."
        );
    }

    private ChatParticipant checkChatParticipant(Long chatRoomId, Long userId) {
        Optional<ChatParticipant> existing = chatParticipantRepository
                .findByChatRoom_RoomIdAndUser_UserId(chatRoomId, userId);

        if (existing.isPresent()) {
            ChatParticipant participant = existing.get();
            if (!participant.getIsActive()) {
                participant.setIsActive(true);
                participant.setJoinedAt(LocalDateTime.now());
                return chatParticipantRepository.save(participant);
            }
            return participant;
        }

        ChatParticipant participant = ChatParticipant.create(getChatRoom(chatRoomId), getUser(userId));
        return chatParticipantRepository.save(participant);
    }

    @Override
    @Transactional
    public ChatResponseDto leaveChatRoom(Long chatRoomId, Long userId) {
        ChatRoom room = getChatRoom(chatRoomId);
        User user = getUser(userId);

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

    private ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatExceptions.ChatRoomNotFoundException(chatRoomId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(userId));
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
