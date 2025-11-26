package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatParticipantListItemDto;
import com.modufit.chat.dto.ChatRoomListItemDto;
import com.modufit.chat.dto.ChatRoomDetailResponseDto;
import com.modufit.chat.dto.PageChatRoomResponseDto;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatMessageRepository;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.repository.customer.ChatRoomCustomerRepository;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.ChatExceptions;
import com.modufit.common.exception.business.FacilityExceptions;
import com.modufit.common.exception.business.UserExceptions;
import com.modufit.facility.dto.FacilitySummaryDto;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.repository.FacilityRepository;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ChatRoomCustomerRepository chatRoomCustomerRepository;

    @Override
    public void createChatRoom(Long facilityId) {
        Facility facility = getFacility(facilityId);

        String chatRoomName = facility.getFacilityName() + "(" + facility.getFacilityType() + ")";
        ChatRoom chatRoom = ChatRoom.of(facility, chatRoomName);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public PageChatRoomResponseDto getUserChatRooms(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = getUser(userId);

        List<ChatRoomListItemDto> chatRoomList = chatRoomCustomerRepository.findChatRooms(userId, pageable);
        Long roomCount = getChatRoomCount(userId);

        return PageChatRoomResponseDto.builder()
                .chatRooms(chatRoomList)
                .roomCount(roomCount)
                .build();
    }

    @Override
    public ChatRoomDetailResponseDto getChatRoomDetail(Long chatRoomId) {
        ChatRoom room = getChatRoomWithFacility(chatRoomId);
        List<ChatParticipantListItemDto> participant = chatParticipantRepository.findChatUsers(chatRoomId);

        return ChatRoomDetailResponseDto.builder()
                .roomId(room.getRoomId())
                .roomName(room.getRoomName())
                .facilitySummary(createFromFacility(room.getFacility()))
                .participantCount(participant.size())
                .participants(participant)
                .build();
    }

    private Long getChatRoomCount(Long userId) {
        return chatParticipantRepository.countByUser_UserId(userId);
    }

    private Facility getFacility(Long facilityId) {
        return facilityRepository.findByFacilityId(facilityId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(facilityId));
    }

    private User getUser(Long userId) {
        return userRepository.findWithProfile(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(userId));
    }

    private ChatRoom getChatRoomWithFacility(Long chatRoomId) {
        return chatRoomRepository.findWithFacility(chatRoomId)
                .orElseThrow(() -> new ChatExceptions.ChatRoomNotFoundException(chatRoomId));
    }

    private FacilitySummaryDto createFromFacility(Facility facility) {
        return FacilitySummaryDto.builder()
                .facilityId(facility.getFacilityId())
                .facilityName(facility.getFacilityName())
                .facilityType(facility.getFacilityType())
                .build();
    }

}
