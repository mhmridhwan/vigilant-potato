package com.project.locker_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.locker_management.model.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {
	List<Locker> findByIsAvailable(boolean isAvailable);
}
