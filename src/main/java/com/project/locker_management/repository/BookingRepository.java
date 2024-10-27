package com.project.locker_management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.locker_management.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserIdAndIsActive(Long userId, boolean isActive);
    Optional<Booking> findByLockerIdAndIsActive(Long lockerId, boolean isActive);
    Optional<Booking> findByLockerIdAndUserIdAndIsActive(Long lockerId, Long userId, boolean isActive);
    
}
