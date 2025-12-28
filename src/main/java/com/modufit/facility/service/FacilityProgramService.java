package com.modufit.facility.service;

import com.modufit.facility.dto.ListFacilityProgramResponseDto;

public interface FacilityProgramService {
    ListFacilityProgramResponseDto getFacilityPrograms(
            Long facilityId, Long userId, Integer page, Integer size);
}
