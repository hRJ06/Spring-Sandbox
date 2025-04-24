package com.Hindol.ShedLocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShedLockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShedLockerApplication.class, args);
	}

}
