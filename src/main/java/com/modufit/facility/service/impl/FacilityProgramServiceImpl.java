package com.modufit.facility.service.impl;

import com.modufit.facility.dto.FacilityProgramListItemDto;
import com.modufit.facility.dto.ListFacilityProgramResponseDto;
import com.modufit.facility.entity.FacilityProgram;
import com.modufit.facility.repository.FacilityProgramRepository;
import com.modufit.facility.service.FacilityProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityProgramServiceImpl implements FacilityProgramService {
    private final FacilityProgramRepository facilityProgramRepository;

    @Override
    public ListFacilityProgramResponseDto getFacilityPrograms(Long facilityId, Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FacilityProgram> facilityProgramPage = facilityProgramRepository.getFacilityPrograms(facilityId, pageable);

        List<FacilityProgramListItemDto> facilityPrograms = new ArrayList<>();
        for (FacilityProgram program : facilityProgramPage.getContent()) {
            facilityPrograms.add(FacilityProgramListItemDto.from(program));
        }

        return ListFacilityProgramResponseDto.builder()
                .programCount(facilityProgramPage.getTotalElements())
                .programs(facilityPrograms)
                .build();
    }
}
