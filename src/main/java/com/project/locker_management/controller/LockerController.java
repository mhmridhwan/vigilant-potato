package com.project.locker_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.locker_management.dto.AccessRequest;
import com.project.locker_management.model.Locker;
import com.project.locker_management.service.LockerService;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @GetMapping("/available")
    public ResponseEntity<List<Locker>> getAvailableLockers() {
        List<Locker> availableLockers = lockerService.findAvailableLockers();
        return ResponseEntity.ok(availableLockers);
    }

    @PostMapping("/{lockerId}/access")
    public ResponseEntity<String> accessLocker(@PathVariable Long lockerId, @RequestBody AccessRequest request) {
        try {
            boolean success = lockerService.accessLocker(lockerId, request.getUserId(), request.getPassword());
            if (success) {
                return ResponseEntity.ok("Access granted");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
