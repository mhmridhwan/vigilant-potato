package com.project.locker_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class LockerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockerManagementApplication.class, args);
	}

}
