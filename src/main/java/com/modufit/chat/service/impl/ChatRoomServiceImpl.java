package com.modufit.chat.service.impl;

import com.modufit.chat.dto.ChatParticipantListItemDto;
import com.modufit.chat.dto.ChatRoomListItemDto;
import com.modufit.chat.dto.ChatRoomDetailResponseDto;
import com.modufit.chat.dto.PageChatRoomResponseDto;
import com.modufit.chat.entity.ChatRoom;
import com.modufit.chat.repository.ChatParticipantRepository;
import com.modufit.chat.repository.ChatRoomRepository;
import com.modufit.chat.repository.customer.ChatRoomCustomerRepository;
import com.modufit.chat.service.ChatRoomService;
import com.modufit.common.exception.business.ChatExceptions;
import com.modufit.common.exception.business.UserExceptions;
import com.modufit.facility.dto.FacilitySummaryDto;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.service.FacilityService;
import com.modufit.users.entity.User;
import com.modufit.users.repository.UserRepository;
import com.modufit.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final FacilityService facilityService;
    private final ChatRoomCustomerRepository chatRoomCustomerRepository;

    @Override
    public ChatRoom getChatRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatExceptions.ChatRoomNotFoundException(roomId));
    }

    @Override
    public void createChatRoom(Long facilityId) {
        Facility facility = facilityService.getFacilityById(facilityId);

        String chatRoomName = facility.getFacilityName() + "(" + facility.getFacilityType() + ")";
        ChatRoom chatRoom = ChatRoom.of(facility, chatRoomName);

        chatRoomRepository.save(chatRoom);
    }

    @Override
    public PageChatRoomResponseDto getUserChatRooms(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.getUserWithProfile(userId);

        Page<ChatRoomListItemDto> chatRoomList = chatRoomCustomerRepository.findChatRooms(userId, pageable);

        return PageChatRoomResponseDto.builder()
                .chatRooms(chatRoomList.getContent())
                .roomCount(chatRoomList.getTotalElements())
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

    private ChatRoom getChatRoomWithFacility(Long chatRoomId) {
        return chatRoomRepository.findWithFacility(chatRoomId)
                .orElseThrow(() -> new ChatExceptions.ChatRoomNotFoundException(chatRoomId));
    }

    private FacilitySummaryDto createFromFacility(Facility facility) {
        return com.modufit.facility.dto.FacilitySummaryDto.builder()
                .facilityId(facility.getFacilityId())
                .facilityName(facility.getFacilityName())
                .facilityType(facility.getFacilityType())
                .build();
    }

}
