package com.cgi.recipe.management.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.exception.CustomRuntimeException;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.response.RecipeResponse;
import com.cgi.recipe.management.system.service.RecipeManagementService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/recipe")
public class RecipeManagementController {

	@Autowired
	RecipeManagementService recipeManagementService;

	@GetMapping("/fetch")
	public List<RecipeResponse> getRecipes() {
		List<Recipe> recipes = recipeManagementService.getAllRecipes();
		List<RecipeResponse> recipeReponseList = new ArrayList();
		recipes.stream().forEach(recipe -> {
			recipeReponseList.add(new RecipeResponse(recipe));
		});
		return recipeReponseList;
	}

	@PostMapping("/add")
	public ResponseEntity<RecipeResponse> addRecipe(@Valid @RequestBody AddRecipeRequest addRecipeRequest) {
		return (ResponseEntity<RecipeResponse>) handleRequest(() -> {
			Recipe recipe = recipeManagementService.addRecipe(addRecipeRequest);
			RecipeResponse recipeResponse = new RecipeResponse(recipe);
			return ResponseEntity.status(HttpStatus.CREATED).body(recipeResponse);
		});
	}

	@PutMapping("/update")
	public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody UpdateRecipeRequest updateRecipeRequest) {
		if (updateRecipeRequest.getRecipeID() == 0) {
			throw new CustomRuntimeException("Validation failed", "Recipe ID is required", HttpStatus.BAD_REQUEST);
		}
		return (ResponseEntity<Recipe>) handleRequest(() -> {
			Recipe recipe = recipeManagementService.updateRecipe(updateRecipeRequest);
			return ResponseEntity.status(HttpStatus.OK).body(recipe);
		});

	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteRecipe(@Valid @RequestParam int id) {

		return (ResponseEntity<String>) handleRequest(() -> {
			String message = recipeManagementService.deleteRecipe(id);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		});

	}

	@GetMapping("/filter/{isVeg}")
	public ResponseEntity<List<Recipe>> filterRecipes(@Valid @PathVariable("isVeg") String isVeg) {

		return (ResponseEntity<List<Recipe>>) handleRequest(() -> {
			List<Recipe> recipe = recipeManagementService.filterRecipesBasedOnIsVeg(isVeg);
			return ResponseEntity.status(HttpStatus.OK).body(recipe);
		});

	}

	@GetMapping("/servings/{noOfServings}/withIngredient/{ingredients}")
	public ResponseEntity<List<Recipe>> findRecipesByNoOfServingsAndIngredients(
			@PathVariable("noOfServings") String noOfServings, @PathVariable("ingredients") Set<String> ingredients) {

		return (ResponseEntity<List<Recipe>>) handleRequest(() -> {
			List<Recipe> recipe = (List<Recipe>) recipeManagementService
					.findRecipesByNoOfServingsAndIngredients(noOfServings, ingredients);
			return ResponseEntity.status(HttpStatus.OK).body(recipe);
		});

	}

	@GetMapping("/withoutIngredient/{ingredient}/withInstructionKeyword/{keyword}")
	public ResponseEntity<List<Recipe>> getRecipesWithoutIngredientAndWithKeyword(
			@PathVariable("ingredient") Set<String> ingredients, @PathVariable("keyword") String keyword) {

		return (ResponseEntity<List<Recipe>>) handleRequest(() -> {
			List<Recipe> recipe = (List<Recipe>) recipeManagementService
					.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword);
			return ResponseEntity.status(HttpStatus.OK).body(recipe);
		});

	}

	private ResponseEntity<?> handleRequest(RequestHandler handler) {
		try {
			return handler.handle();
		} catch (CustomRuntimeException e) {
			throw new CustomRuntimeException("Validation failed. Please review your request", e.getMessage(),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			throw new CustomRuntimeException("Failed to process request", e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	interface RequestHandler {
		ResponseEntity<?> handle() throws Exception;
	}

}
