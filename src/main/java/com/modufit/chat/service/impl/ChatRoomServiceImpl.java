package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatRoomResponseDto;
import com.modufit.chat.entity.ChatMessage;
import com.modufit.chat.entity.ChatParticipant;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatMessageRepository;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.repository.customer.ChatRoomCustomerRepository;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.ChatExceptions;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityScheduleRepository facilityScheduleRepository;
    private final ChatRoomCustomerRepository chatRoomCustomerRepository;

    @Override
    public void createChatRoom(Long facilityId, Long scheduleId) {
        Facility facility = getFacility(facilityId);
        FacilitySchedule facilitySchedule = getFacilitySchedule(facilityId, scheduleId);

        String chatRoomName = facility.getFacilityName() + "(" + facility.getFacilityType() + ")";
        ChatRoom chatRoom = ChatRoom.of(facilitySchedule, facility, chatRoomName);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoomResponseDto> getChatRooms(Long userId) {
        User user = getUser(userId);
        return chatRoomCustomerRepository.findChatRooms(userId);
    }

    @Override
    public Long getChatRoomCount(Long userId) {
        User user = getUser(userId);
        return chatParticipantRepository.countByUser_UserId(userId);
    }

    private Facility getFacility(Long facilityId) {
        return facilityRepository.findByFacilityId(facilityId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(facilityId));

    }

    private FacilitySchedule getFacilitySchedule(Long facilityId, Long scheduleId) {
        return facilityScheduleRepository.findByFacility_FacilityIdAndScheduleId(facilityId, scheduleId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(scheduleId));

    }

    private User getUser(Long userId) {
        return userRepository.findWithProfile(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(userId));
    }

}
