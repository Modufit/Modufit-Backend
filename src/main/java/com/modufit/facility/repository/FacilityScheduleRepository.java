package com.modufit.facility.repository;

import com.modufit.facility.entity.FacilitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityScheduleRepository extends JpaRepository<FacilitySchedule, Long> {
    Optional<FacilitySchedule> findByFacility_FacilityIdAndScheduleId(Long facilityId, Long scheduleId);
}
