package com.modufit.users.repository;

import com.modufit.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.userProfile WHERE u.userId = :userId")
    Optional<User> findWithProfile(@Param("userId") Long userId);

}
