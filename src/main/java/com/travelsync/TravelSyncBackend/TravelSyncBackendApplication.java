package com.travelsync.TravelSyncBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelSyncBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelSyncBackendApplication.class, args);
		System.out.println("Backend is running");
	}

}
