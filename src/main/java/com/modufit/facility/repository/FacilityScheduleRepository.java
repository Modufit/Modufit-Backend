package com.modufit.facility.repository;

import com.modufit.facility.entity.FacilitySchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityScheduleRepository extends JpaRepository<FacilitySchedule, Long> {

    @EntityGraph(attributePaths = {"facility"})
    Page<FacilitySchedule> getFacilitySchedules(@Param("facilityId") Long facilityId, Pageable pageable);
}
