package com.project.locker_management.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.locker_management.model.Booking;
import com.project.locker_management.model.Locker;
import com.project.locker_management.model.User;
import com.project.locker_management.repository.BookingRepository;
import com.project.locker_management.repository.UserRepository;

@Service
public class BookingService {

    private static final BigDecimal DEPOSIT_RATE = new BigDecimal("10000.00");
    private static final BigDecimal LATE_FEE_RATE = new BigDecimal("5000.00");

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LockerService lockerService;

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private EmailService emailService;

    public Boolean createBooking(Long userId, List<Long> lockerIds, int days) {
        if (lockerIds.size() > 3) {
            throw new IllegalArgumentException("Cannot book more than 3 lockers at a time.");
        }

        BigDecimal totalDeposit = DEPOSIT_RATE.multiply(new BigDecimal(days));

        List<Locker> bookedLockers = lockerIds.stream()
                .map(lockerService::bookLocker)
                .collect(Collectors.toList());
        
        for (Locker locker : bookedLockers) {

            Booking booking = new Booking();
            
            @SuppressWarnings("deprecation")
    		User user = userRepository.getOne(userId);
            booking.setUser(user);
            booking.setLocker(locker);
            booking.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
            booking.setEndDate(Timestamp.valueOf(LocalDateTime.now().plusDays(days)));
            booking.setDeposit(totalDeposit);
            booking.setPassword(generatePassword());
            booking.setIsActive(true);

            Booking savedBooking = bookingRepository.save(booking);
            // Record the initial deposit transaction
            transactionService.createTransaction(savedBooking, totalDeposit, "deposit");
            
            String text = "Locker Number: " + locker.getLockerNumber() + "\nPassword: " + savedBooking.getPassword();
            emailService.sendEmail(user.getEmail(), "Booking Confirmation", text);
		}

        return true;
    }

    public Booking endBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

//        booking.setEndDate(Timestamp.valueOf(LocalDateTime.now()));
        booking.setIsActive(false);

        long totalBorrowDays = ChronoUnit.DAYS.between(booking.getStartDate().toLocalDateTime(), booking.getEndDate().toLocalDateTime());
        long actualTotalBorrowDays = ChronoUnit.DAYS.between(booking.getStartDate().toLocalDateTime(), LocalDateTime.now()); 
        //dihitung denda kalau lebih dari 1 hari. kalau masih lewat 1 hari tidak ada denda
        long overdueDaysExcuse = (actualTotalBorrowDays - totalBorrowDays) - 1;
        long overdueDaysReal = (actualTotalBorrowDays - totalBorrowDays);
        BigDecimal fine = BigDecimal.ZERO;

        if (overdueDaysExcuse > 0) {
        	//yang dijadikan bahan perkalian adalah overdue yang real
            fine = LATE_FEE_RATE.multiply(new BigDecimal(overdueDaysReal));
            transactionService.createTransaction(booking, fine, "fine");
        }

        //karena denda dibayar langsung ditempat, maka dari itu tidak dimasukkan ke dalam sistem
        BigDecimal refund = booking.getDeposit().subtract(fine);
        if (refund.compareTo(BigDecimal.ZERO) > 0) {
            transactionService.createTransaction(booking, refund, "refund");
        }

       lockerService.releaseLocker(booking.getLocker().getId());

        return bookingRepository.save(booking);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
