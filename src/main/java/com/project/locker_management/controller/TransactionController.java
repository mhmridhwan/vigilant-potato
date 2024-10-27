package com.project.locker_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.locker_management.model.Transaction;
import com.project.locker_management.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Transaction>> getTransactionsByBooking(@PathVariable Long bookingId) {
        List<Transaction> transactions = transactionService.getTransactionsByBookingId(bookingId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
