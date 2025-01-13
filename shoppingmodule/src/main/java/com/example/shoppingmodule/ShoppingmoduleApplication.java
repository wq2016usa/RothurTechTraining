package com.example.shoppingmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ShoppingmoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmoduleApplication.class, args);
	}

}
