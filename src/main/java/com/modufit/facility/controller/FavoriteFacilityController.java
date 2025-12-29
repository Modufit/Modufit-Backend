package com.modufit.facility.controller;

import com.modufit.common.ApiResponse;
import com.modufit.facility.dto.FavoriteFacilityResponseDto;
import com.modufit.facility.service.FavoriteFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FavoriteFacilityController {
    private final FavoriteFacilityService favoriteFacilityService;

    @GetMapping("/{userId}/favorite")
    public ResponseEntity<ApiResponse<FavoriteFacilityResponseDto>> getFavoriteFacilities(
            @PathVariable Long userId) {
        FavoriteFacilityResponseDto favoriteFacility = favoriteFacilityService.getFavoriteFacilities(userId);
        return ResponseEntity.ok(ApiResponse.success("사용자 관심 시설 조회", favoriteFacility));
    }

    @PostMapping("/{userId}/favorite")
    public ResponseEntity<ApiResponse<Void>> addFavoriteFacility(
            @PathVariable Long userId, @RequestParam Long facilityId) {
        favoriteFacilityService.insertFavoriteFacility(facilityId, userId);
        return ResponseEntity.ok(ApiResponse.success("관심 시설 등록 성공"));
    }

    @DeleteMapping("/{userId}/favorite")
    public ResponseEntity<ApiResponse<Void>> deleteFavoriteFacility(
            @PathVariable Long userId, @RequestParam Long favoriteId) {
        favoriteFacilityService.deleteFavoriteFacility(favoriteId, userId);
        return ResponseEntity.ok(ApiResponse.success("관심 시설 삭제 성공"));
    }
}
