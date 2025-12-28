package com.modufit.facility.repository;

import com.modufit.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findByFacilityId(Long facilityId);

    @Query("select f from Facility f join fetch f.chatRoom where f.facilityId = :facilityId")
    Facility findFacilityWithChatRoom(@Param("facilityId") Long facilityId);
}
