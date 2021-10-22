package com.breakbooking.eventbookingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EventbookingapiEmailapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingapiEmailapiApplication.class, args);
	}
	@GetMapping("/")
	public String root() {
		return "Welcome to email service";
	}
}
