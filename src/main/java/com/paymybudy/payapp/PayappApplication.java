package com.paymybudy.payapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class PayappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayappApplication.class, args);
	}

}
