package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatRequestDto;
import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.dto.enums.MessageType;
import com.modufit.chat.entity.ChatMessage;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatMessageRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.service.ChatMessageService;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.ChatExceptions;
import com.modufit.common.exception.business.UserExceptions;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import com.modufit.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Override
    @Transactional
    public ChatResponseDto saveMessage(ChatRequestDto messageDto) {
        ChatRoom room = chatRoomService.getChatRoomById(messageDto.getChatRoomId());
        User sender = userService.getUserWithProfile(messageDto.getSenderId());

        ChatMessage chat = ChatMessage.of(messageDto.getMessage(), sender, room);
        ChatMessage saved = chatMessageRepository.save(chat);

        return createFromEntity(saved);
    }

    @Override
    public List<ChatResponseDto> getMessagesBefore(Long chatRoomId, Long lastMessageId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<ChatMessage> messages = chatMessageRepository.getMessagesBefore(chatRoomId, lastMessageId, pageable);

        return messages.stream()
                .limit(limit)
                .map(this::createFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatResponseDto> getMessages(Long chatRoomId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<ChatMessage> messages = chatMessageRepository.getRecentMessages(chatRoomId, pageable);

        return messages.stream()
                .limit(limit)
                .map(this::createFromEntity)
                .collect(Collectors.toList());
    }

    private ChatResponseDto createFromEntity(ChatMessage message) {
        return ChatResponseDto.builder()
                .messageType(MessageType.TALK)
                .chatRoomId(message.getChatRoom().getRoomId())
                .senderId(message.getSender().getUserId())
                .senderName(message.getSender().getUserProfile().getUserName())
                .message(message.getMessage())
                .sendTime(message.getSentAt())
                .build();
    }
}
