package com.project.locker_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.locker_management.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
    Optional<User> findByKtpNumber(String ktpNumber);
}
