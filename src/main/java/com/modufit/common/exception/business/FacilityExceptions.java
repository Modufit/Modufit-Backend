package com.modufit.common.exception.business;

import com.modufit.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FacilityExceptions {

    public static class FacilityNotFoundException extends BusinessException {
        public FacilityNotFoundException(Long facilityId) {
            super(
                    "FACILITY_NOT_FOUND",
                    "해당 시설(ID: " + facilityId + ")을 찾을 수 없습니다.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public static class FacilityScheduleNotFoundException extends BusinessException {
        public FacilityScheduleNotFoundException(Long scheduleId) {
            super(
                    "FACILITY_SCHEDULE_NOT_FOUND",
                    "해당 시설 스케줄(ID: " + scheduleId + ")을 찾을 수 없습니다.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public static class FacilityScheduleConflictException extends BusinessException {
        public FacilityScheduleConflictException(Long scheduleId) {
            super(
                    "FACILITY_SCHEDULE_CONFLICT",
                    "해당 스케줄(ID: " + scheduleId + ")이 다른 예약과 겹칩니다.",
                    HttpStatus.CONFLICT
            );
        }
    }
}