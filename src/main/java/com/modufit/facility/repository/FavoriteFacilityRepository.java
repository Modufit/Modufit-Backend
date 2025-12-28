package com.modufit.facility.repository;

import com.modufit.facility.entity.FavoriteFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteFacilityRepository extends JpaRepository<FavoriteFacility, Long> {
    Boolean existsByUser_UserIdAndFacility_FacilityId(Long userId, Long facilityId);

    @Query("SELECT ff FROM FavoriteFacility ff " +
            "JOIN FETCH ff.facility WHERE ff.favoriteId = :favoriteId")
    Optional<FavoriteFacility> findByFavoriteId(@Param("favoriteId") Long favoriteId);

    @Query("SELECT ff FROM FavoriteFacility ff " +
            "JOIN FETCH ff.facility WHERE ff.user.userId = :userId")
    List<FavoriteFacility> findFavoriteFacilities(@Param("userId") Long userId);
}
