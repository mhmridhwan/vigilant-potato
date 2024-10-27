package com.project.locker_management.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.locker_management.model.Booking;
import com.project.locker_management.model.Transaction;
import com.project.locker_management.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Booking booking, BigDecimal amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setBooking(booking);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getTransactionsByBookingId(Long booking_id) {
        List<Transaction> listTransaction = transactionRepository.findByBookingId(booking_id);
        return listTransaction;
    }
}

