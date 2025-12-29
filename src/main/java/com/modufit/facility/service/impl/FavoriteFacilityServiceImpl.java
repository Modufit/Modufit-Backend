package com.modufit.facility.service.impl;

import com.modufit.common.exception.business.FacilityExceptions;
import com.modufit.facility.dto.FacilitySummaryDto;
import com.modufit.facility.dto.FavoriteFacilityResponseDto;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.entity.FavoriteFacility;
import com.modufit.facility.repository.FavoriteFacilityRepository;
import com.modufit.facility.service.FacilityService;
import com.modufit.facility.service.FavoriteFacilityService;
import com.modufit.users.entity.User;
import com.modufit.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteFacilityServiceImpl implements FavoriteFacilityService {
    private final FavoriteFacilityRepository favoriteFacilityRepository;
    private final UserService userService;
    private final FacilityService facilityService;

    @Override
    public Boolean isLiked(Long facilityId, Long userId) {
        return favoriteFacilityRepository.existsByUser_UserIdAndFacility_FacilityId(userId, facilityId);
    }

    @Override
    public FavoriteFacilityResponseDto getFavoriteFacilities( Long userId) {
        List<FavoriteFacility> favoriteFacilities = favoriteFacilityRepository.findFavoriteFacilities(userId);

        List<FacilitySummaryDto> summaries = new ArrayList<>();
        for (FavoriteFacility favoriteFacility : favoriteFacilities) {
            summaries.add(FacilitySummaryDto.from(favoriteFacility.getFacility()));
        }

        return FavoriteFacilityResponseDto.builder()
                .userId(userId)
                .favoriteCount(favoriteFacilities.size())
                .facilitySummaries(summaries)
                .build();
    }

    @Override
    public void insertFavoriteFacility(Long facilityId, Long userId) {
        User user = userService.getUserById(userId);
        Facility facility = facilityService.getFacilityById(facilityId);

        favoriteFacilityRepository.save(FavoriteFacility.of(user,facility));
    }

    @Override
    public void deleteFavoriteFacility(Long favoriteId, Long userId) {
        FavoriteFacility favoriteFacility = favoriteFacilityRepository.findByFavoriteId(favoriteId)
                .orElseThrow(()-> new FacilityExceptions.FavoriteFacilityNotFoundException(favoriteId));

        if(!isLiked(favoriteFacility.getFavoriteId(), userId)) {
            throw new FacilityExceptions.FavoriteFacilityNotFoundException(favoriteFacility.getFavoriteId());
        }

        favoriteFacilityRepository.delete(favoriteFacility);
    }
}
