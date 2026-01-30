package com.modufit.facility.repository;

import com.modufit.facility.entity.LikedFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedFacilityRepository extends JpaRepository<LikedFacility, Long> {

}
