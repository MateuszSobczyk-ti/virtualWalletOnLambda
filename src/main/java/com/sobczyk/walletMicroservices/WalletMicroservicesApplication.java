package com.sobczyk.walletMicroservices;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class WalletMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletMicroservicesApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
		System.out.println("Aktualny czas(Europe/Warsaw) = " + LocalDateTime.now());
	}
}