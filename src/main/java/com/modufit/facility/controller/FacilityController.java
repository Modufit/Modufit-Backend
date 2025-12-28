package com.modufit.facility.controller;

import com.modufit.common.ApiResponse;
import com.modufit.facility.dto.FacilityDetailResponseDto;
import com.modufit.facility.dto.ListFacilityProgramResponseDto;
import com.modufit.facility.dto.ListFacilityScheduleResponseDto;
import com.modufit.facility.service.FacilityProgramService;
import com.modufit.facility.service.FacilityScheduleService;
import com.modufit.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    private final FacilityProgramService facilityProgramService;
    private final FacilityScheduleService facilityScheduleService;

    @GetMapping("/{facilityId}/detail")
    public ResponseEntity<ApiResponse<FacilityDetailResponseDto>> getFacilityDetail(
            @PathVariable Long facilityId,
            @RequestParam Long userId
    ) {
        FacilityDetailResponseDto facility = facilityService.getFacilityDetail(facilityId, userId);
        return ResponseEntity.ok(ApiResponse.success("시설 상세 조회", facility));
    }

    @GetMapping("/{facilityId}/programs")
    public ResponseEntity<ApiResponse<ListFacilityProgramResponseDto>> getFacilityPrograms(
            @PathVariable Long facilityId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size
    ) {
        ListFacilityProgramResponseDto facilityPrograms =
                facilityProgramService.getFacilityPrograms(facilityId, userId, page, size);
        return ResponseEntity.ok(ApiResponse.success("시설 프로그램 리스트 조회", facilityPrograms));
    }

    @GetMapping("/{facilityId}/schedules")
    public ResponseEntity<ApiResponse<ListFacilityScheduleResponseDto>> getFacilitySchedules(
            @PathVariable Long facilityId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size
    ) {
        ListFacilityScheduleResponseDto schedules =
                facilityScheduleService.getFacilitySchedules(facilityId, userId, page, size);
        return ResponseEntity.ok(ApiResponse.success("시설 스케줄 리스트 조회", schedules));
    }
}
