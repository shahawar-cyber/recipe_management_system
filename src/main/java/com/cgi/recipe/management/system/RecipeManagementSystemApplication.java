package com.cgi.recipe.management.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.cgi.recipe.management.system.*")
@EntityScan("com.cgi.recipe.management.system.*")
@EnableJpaRepositories("com.cgi.recipe.management.system.*")
public class RecipeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementSystemApplication.class, args);
	}

}
