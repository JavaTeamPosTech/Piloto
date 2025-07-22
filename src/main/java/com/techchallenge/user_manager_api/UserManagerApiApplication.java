package com.techchallenge.user_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.techchallenge.user_manager_api.infra.repositories")
@EntityScan(basePackages = "com.techchallenge.user_manager_api.infra.model")
public class UserManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagerApiApplication.class, args);
	}

}
