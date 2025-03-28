package com.leveluplove.leveluplove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.leveluplove.leveluplove.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class LevelUpLoveApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelUpLoveApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository repo) {
		return args -> { };
	}
}
