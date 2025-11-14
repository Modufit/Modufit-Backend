package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatRoomResponseDto;
import com.modufit.chat.entity.ChatMessage;
import com.modufit.chat.entity.ChatParticipant;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatMessageRepository;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.FacilityExceptions;
import com.modufit.common.exception.business.UserExceptions;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.entity.FacilitySchedule;
import com.modufit.facility.repository.FacilityRepository;
import com.modufit.facility.repository.FacilityScheduleRepository;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityScheduleRepository facilityScheduleRepository;

    @Override
    public void createChatRoom(Long facilityId, Long scheduleId) {
        Facility facility = facilityRepository.findByFacilityId(facilityId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(facilityId));

        FacilitySchedule facilitySchedule = facilityScheduleRepository.findByFacility_FacilityIdAndScheduleId(facilityId, scheduleId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(scheduleId));

        String chatRoomName = facility.getFacilityName() + "(" + facility.getFacilityType() + ")";
        ChatRoom chatRoom = ChatRoom.of(facilitySchedule, facility, chatRoomName);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void closedChatRoom(Long roomId, Long facilityId, Long scheduleId) {

    }

    @Override
    public List<ChatRoomResponseDto> getChatRooms(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(userId));

        List<ChatParticipant> participantList = chatParticipantRepository.findByUser_userId(userId);

        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();
        for (ChatParticipant chatParticipant : participantList) {
            long unReadMessageNum = countUnReadMessages(chatParticipant.getChatRoom().getRoomId(), chatParticipant.getLastReadMessageId());
            ChatMessage message = currentMessage(chatParticipant.getChatRoom().getRoomId());

            ChatRoomResponseDto chatRoomResponseDto
                    = ChatRoomResponseDto.builder()
                    .roomName(chatParticipant.getChatRoom().getRoomName())
                    .roomId(chatParticipant.getChatRoom().getRoomId())
                    .maxParticipants(chatParticipant.getChatRoom().getSchedule().getMaxParticipants())
                    .currentParticipants(chatParticipant.getChatRoom().getSchedule().getCurrentParticipants())
                    .unreadMessagesCount(unReadMessageNum)
                    .message(message==null?"대화가 없습니다.":message.getMessage())
                    .sentTime(message==null?null:message.getSentAt())
                    .isActive(chatParticipant.getChatRoom().getIsActive())
                    .build();

            chatRoomResponseDtoList.add(chatRoomResponseDto);
        }

        return chatRoomResponseDtoList;
    }

    private Long countUnReadMessages(Long chatRoomId, Long lastReadMessageId) {
        return chatMessageRepository.countUnreadMessages(chatRoomId, lastReadMessageId);
    }

    private ChatMessage currentMessage(Long chatRoomId) {
        return chatMessageRepository.findTopByChatRoom_RoomIdOrderBySentAtDesc(chatRoomId);
    }
}
