package com.project.locker_management.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.locker_management.dto.BookingRequest;
import com.project.locker_management.model.Booking;
import com.project.locker_management.model.Locker;
import com.project.locker_management.service.BookingService;
import com.project.locker_management.service.EmailService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest) {
        try {
            Boolean result = bookingService.createBooking(
                    bookingRequest.getUserId(), 
                    bookingRequest.getLockerIds(), 
                    bookingRequest.getDays()
            );

            // Send email with locker and password info
//            emailService.sendBookingConfirmation(
//                    booking.getUser().getEmail(),
//                    booking.getLocker().stream().map(Locker::getLockerNumber).collect(Collectors.joining(", ")),
//                    booking.getPassword()
//            );

            return new ResponseEntity<>("data sukses", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{bookingId}/end")
    public ResponseEntity<Booking> endBooking(@PathVariable Long bookingId) {
        try {
            Booking endedBooking = bookingService.endBooking(bookingId);
            return new ResponseEntity<>(endedBooking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
