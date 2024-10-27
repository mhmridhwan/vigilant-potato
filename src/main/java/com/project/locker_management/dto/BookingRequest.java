package com.project.locker_management.dto;

import java.util.List;

public class BookingRequest {
    public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getLockerIds() {
		return lockerIds;
	}
	public void setLockerIds(List<Long> lockerIds) {
		this.lockerIds = lockerIds;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	private Long userId;
    private List<Long> lockerIds;
    private int days;

    // Getters and Setters
    
}
