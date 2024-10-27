package com.project.locker_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.locker_management.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByBookingId(Long bookingId);
}
