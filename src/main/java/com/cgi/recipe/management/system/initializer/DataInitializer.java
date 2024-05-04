package com.cgi.recipe.management.system.initializer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

	private RecipeManagementService recipeManagementService;
	private final ResourceLoader resourceLoader;
	private final DataSource dataSource;

	@Autowired
	public DataInitializer(RecipeManagementService recipeManagementService, ResourceLoader resourceLoader,
			DataSource dataSource) {
		this.recipeManagementService = recipeManagementService;
		this.resourceLoader = resourceLoader;
		this.dataSource = dataSource;
	}

	@PostConstruct
	public void init() {
		createDatabaseSchema();
		loadDataFromJson();
	}

	private void createDatabaseSchema() {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			boolean tableExists = metaData
					.getTables("recipie_management_system", "recipe_management_system", "recipe", null).next();
			if (!tableExists) {
				connection.createStatement().executeUpdate(
						"CREATE TABLE recipe_management_system.recipe_ingredients ( recipe_id INTEGER, ingredients VARCHAR)");
				connection.createStatement().executeUpdate(
						"CREATE TABLE recipe_management_system.recipe (id SERIAL PRIMARY KEY,dishName VARCHAR(255),isVeg BOOLEAN,noOfServings VARCHAR(100),ingredients TEXT[],instructions TEXT)");
			}
		} catch (SQLException e) {
			throw new CustomRuntimeException("Failed to create database schema", e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void loadDataFromJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		Resource resource = resourceLoader.getResource("classpath:recipe.json");

		try (InputStream inputStream = resource.getInputStream()) {
			List<AddRecipeRequest> addRecipeRequests = objectMapper.readValue(inputStream,
					new TypeReference<List<AddRecipeRequest>>() {
					});
			for (AddRecipeRequest addRecipeRequest : addRecipeRequests) {
				recipeManagementService.addRecipe(addRecipeRequest);
			}
		} catch (IOException e) {
			throw new CustomRuntimeException("Failed to initialize data from JSON file", e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
