package com.modufit.facility.service;

import com.modufit.facility.dto.FavoriteFacilityResponseDto;
import com.modufit.facility.entity.Facility;
import com.modufit.facility.entity.FavoriteFacility;
import com.modufit.users.entity.User;

public interface FavoriteFacilityService {
    Boolean isLiked(Long facilityId, Long userId);
    FavoriteFacilityResponseDto getFavoriteFacilities(Long userId);
    void insertFavoriteFacility(Long facilityId, Long userId);
    void deleteFavoriteFacility(Long favoriteId, Long userId);
}
