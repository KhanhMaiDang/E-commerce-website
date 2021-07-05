package com.example.ecommerceweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ECommerceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceWebApplication.class, args);
	}

}
