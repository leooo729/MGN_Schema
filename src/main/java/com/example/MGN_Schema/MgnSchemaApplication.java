package com.example.MGN_Schema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MgnSchemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgnSchemaApplication.class, args);
	}

}
