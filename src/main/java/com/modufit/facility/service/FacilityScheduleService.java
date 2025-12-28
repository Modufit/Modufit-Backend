package com.modufit.facility.service;

import com.modufit.facility.dto.ListFacilityScheduleResponseDto;

public interface FacilityScheduleService {
    ListFacilityScheduleResponseDto getFacilitySchedules(
            Long facilityId, Long userId, Integer page, Integer size);
}
