package com.modufit.facility.service;

import com.modufit.facility.dto.FacilityDetailResponseDto;
import com.modufit.facility.entity.Facility;

public interface FacilityService {
    Facility getFacilityById(Long facilityId);
    FacilityDetailResponseDto getFacilityDetail(Long facilityId, Long userId);
}
