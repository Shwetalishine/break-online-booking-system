package com.breakbooking.eventbookingapi;

import jdk.jfr.Registered;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@RestController
public class EventbookingapiPaymentapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingapiPaymentapiApplication.class, args);
	}
	@GetMapping("/")
	public String root() {
		return "Welcome to payment service";
	}
}
