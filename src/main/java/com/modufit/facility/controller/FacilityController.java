package com.modufit.facility.controller;

import com.modufit.common.ApiResponse;
import com.modufit.facility.dto.FacilityDetailResponseDto;
import com.modufit.facility.dto.ListFacilityProgramResponseDto;
import com.modufit.facility.dto.ListFacilityScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facility")
@RequiredArgsConstructor
public class FacilityController {

    @GetMapping("/{facilityId}/detail")
    public ResponseEntity<ApiResponse<FacilityDetailResponseDto>> getFacilityDetail(
            @PathVariable Long facilityId,
            @RequestParam Long userId
    ) {
        FacilityDetailResponseDto facility = null;
        return ResponseEntity.ok(ApiResponse.success("시설 상세 조회", facility));
    }

    @GetMapping("/{facilityId}/programs")
    public ResponseEntity<ApiResponse<ListFacilityProgramResponseDto>> getFacilityPrograms(
            @PathVariable Long facilityId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size
    ) {
        ListFacilityProgramResponseDto facilityPrograms = null;
        return ResponseEntity.ok(ApiResponse.success("시설 프로그램 리스트 조회", facilityPrograms));
    }

    @GetMapping("/{facilityId}/schedules")
    public ResponseEntity<ApiResponse<ListFacilityScheduleResponseDto>> getFacilitySchedules(
            @PathVariable Long facilityId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "8", required = false) int size
    ) {
        ListFacilityScheduleResponseDto schedules = null;
        return ResponseEntity.ok(ApiResponse.success("시설 스케줄 리스트 조회", schedules));
    }
}
