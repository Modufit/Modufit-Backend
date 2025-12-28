package com.modufit.facility.service.impl;

import com.modufit.common.exception.business.FacilityExceptions;
import com.modufit.facility.dto.FacilityDetailDto;
import com.modufit.facility.dto.FacilityDetailResponseDto;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.repository.FacilityRepository;
import com.modufit.facility.service.FacilityService;
import com.modufit.facility.service.FavoriteFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final FavoriteFacilityService favoriteFacilityService;

    @Override
    public Facility getFacilityById(Long facilityId) {
        return facilityRepository.findByFacilityId(facilityId)
                .orElseThrow(() -> new FacilityExceptions.FacilityNotFoundException(facilityId));
    }

    @Override
    public FacilityDetailResponseDto getFacilityDetail(Long facilityId, Long userId) {
        Facility facility = facilityRepository.findFacilityWithChatRoom(facilityId);
        Boolean userLiked = userId != null
                ? favoriteFacilityService.isLiked(userId, facilityId)
                : null;

        return FacilityDetailResponseDto.builder()
                .facilityDetail(createFromFacility(facility))
                .facilityLiked(userLiked)
                .build();
    }

    private FacilityDetailDto createFromFacility(Facility facility) {
        return FacilityDetailDto.builder()
                .facilityId(facility.getFacilityId())
                .chatRoomId(facility.getChatRoom().getRoomId())
                .facilityName(facility.getFacilityName())
                .facilityType(facility.getFacilityType())
                .sportType(facility.getSportType())
                .address(facility.getAddress())
                .openWeekday(facility.getOpenWeekday())
                .build();
    }
}
