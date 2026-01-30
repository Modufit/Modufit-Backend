package com.modufit.facility.service;

import com.modufit.facility.dto.FavoriteFacilityResponseDto;

public interface FavoriteFacilityService {
    Boolean isLiked(Long facilityId, Long userId);
    FavoriteFacilityResponseDto getFavoriteFacilities(Long userId);
    void insertFavoriteFacility(Long facilityId, Long userId);
    void deleteFavoriteFacility(Long favoriteId, Long userId);
}
