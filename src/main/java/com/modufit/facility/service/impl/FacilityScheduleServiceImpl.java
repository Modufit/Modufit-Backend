package com.modufit.facility.service.impl;

import com.modufit.facility.dto.FacilityScheduleListItemDto;
import com.modufit.facility.dto.ListFacilityScheduleResponseDto;
import com.modufit.facility.entity.FacilitySchedule;
import com.modufit.facility.repository.FacilityScheduleRepository;
import com.modufit.facility.service.FacilityScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityScheduleServiceImpl implements FacilityScheduleService {

    private final FacilityScheduleRepository facilityScheduleRepository;

    @Override
    public ListFacilityScheduleResponseDto getFacilitySchedules(Long facilityId, Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FacilitySchedule> facilitySchedulePage = facilityScheduleRepository.getFacilitySchedules(facilityId, pageable);

        List<FacilityScheduleListItemDto> facilitySchedule = new ArrayList<>();
        for (FacilitySchedule schedule : facilitySchedulePage.getContent()) {
            facilitySchedule.add(FacilityScheduleListItemDto.from(schedule));
        }

        return ListFacilityScheduleResponseDto.builder()
                .scheduleCount(facilitySchedulePage.getTotalElements())
                .facilitySchedules(facilitySchedule)
                .build();
    }
}
