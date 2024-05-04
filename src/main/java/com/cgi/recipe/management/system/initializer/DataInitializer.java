package com.cgi.recipe.management.system.initializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cgi.recipe.management.system.exception.CustomRuntimeException;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.service.RecipeManagementService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
	
	@Autowired
    private RecipeManagementService recipeManagementService;
	
	@PostConstruct
	public void init() {
		ObjectMapper objectMapper = new ObjectMapper();

		File file;
		try {
			file = new ClassPathResource("recipe.json").getFile();
			List<AddRecipeRequest> addRecipeRequests = objectMapper.readValue(file,
					new TypeReference<List<AddRecipeRequest>>() {
					});
			for (AddRecipeRequest addRecipeRequest : addRecipeRequests) {
				recipeManagementService.addRecipe(addRecipeRequest);
			}

		} catch (IOException e) {
			throw new CustomRuntimeException(" IO exception has ocurred ", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
