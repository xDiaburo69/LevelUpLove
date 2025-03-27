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
		return args -> {
//			User admin1 = new User();
//			admin1.setUsername("diaburo");
//			admin1.setEmail("diaburo@example.com");
//			admin1.setPassword("KanniBale84!");
//			admin1.setRole(Roles.ADMIN);
//
//			User admin2 = new User();
//			admin2.setUsername("madame_parker");
//			admin2.setEmail("madame_parker@example.com");
//			admin2.setPassword("Password123!");
//			admin2.setRole(Roles.ADMIN);
//
//			repo.saveAll(List.of(admin1, admin2));
		};
	}
}
