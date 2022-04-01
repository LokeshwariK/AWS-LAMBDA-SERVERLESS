package com.awslambda.apiregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRegistryApplication.class, args);
	}

}
