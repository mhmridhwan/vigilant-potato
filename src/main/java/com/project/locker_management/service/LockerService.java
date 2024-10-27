package com.project.locker_management.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.locker_management.model.Booking;
import com.project.locker_management.model.Locker;
import com.project.locker_management.repository.BookingRepository;
import com.project.locker_management.repository.LockerRepository;

@Service
public class LockerService {
	
	private static final BigDecimal RELEASE_LOCKER_FINE = new BigDecimal("25000.00");

    @Autowired
    private LockerRepository lockerRepository;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private BookingRepository bookingRepository;

    public List<Locker> findAvailableLockers() {
        return lockerRepository.findByIsAvailable(true);
    }

    public Locker bookLocker(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new RuntimeException("Locker not found"));
        if (!locker.getIsAvailable()) {
            throw new RuntimeException("One or all selected Locker already booked");
        }
        locker.setIsAvailable(false);
        return lockerRepository.save(locker);
    }

    public Locker releaseLocker(Integer lockerId) {
        @SuppressWarnings("removal")
		Locker locker = lockerRepository.findById(new Long(lockerId))
                .orElseThrow(() -> new RuntimeException("Locker not found"));
        locker.setIsAvailable(true);
        return lockerRepository.save(locker);
    }
    
    public boolean accessLocker(Long lockerId, Long userId, String password) {
        // Ambil locker berdasarkan ID
        Optional<Booking> bookingOptional = bookingRepository.findByLockerIdAndUserIdAndIsActive(lockerId, userId, true);
        if (!bookingOptional.isPresent()) {
            throw new IllegalArgumentException("Locker not found");
        }

        Booking booking = bookingOptional.get();
        Integer accessAttempt = (booking.getAccessAttempts() != null) ? booking.getAccessAttempts() : 0;
        // Periksa password
        if (!booking.getPassword().equals(password)) {
        	booking.setAccessAttempts(++accessAttempt);
        	bookingRepository.save(booking);
        	if (accessAttempt >= 3) {
				transactionService.createTransaction(booking, RELEASE_LOCKER_FINE, "fine");
				Locker locker = releaseLocker(lockerId.intValue());
				
				//booking tsb dibuat tidak aktif
				booking.setIsActive(false);
				bookingRepository.save(booking);
			}
            return false; // Password tidak valid || sudah akese 3x
        }

        // Jika password valid, logika untuk mengakses locker dapat ditambahkan di sini
        // Misalnya, mencatat waktu akses, dll.
        
        return true; // Akses diizinkan
    }
}

