package com.project.locker_management.dto;

public class AccessRequest {
    public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private Long userId;
    private String password;

    // Getters and Setters
    
}