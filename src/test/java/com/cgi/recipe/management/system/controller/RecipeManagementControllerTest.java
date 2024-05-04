package com.cgi.recipe.management.system.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.exception.CustomRuntimeException;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.response.RecipeResponse;
import com.cgi.recipe.management.system.service.RecipeManagementService;

@RunWith(MockitoJUnitRunner.class)
public class RecipeManagementControllerTest {

	@Mock
	private RecipeManagementService recipeManagementService;

	@InjectMocks
	private RecipeManagementController recipeManagementController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetRecipes() {
		List<Recipe> recipes = new ArrayList<>();
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		recipes.add(new Recipe(addRecipeRequest));
		when(recipeManagementService.getAllRecipes()).thenReturn(recipes);

		List<RecipeResponse> response = recipeManagementController.getRecipes();

		assertEquals(recipes.size(), response.size());
	}

	@Test
	public void testAddRecipe() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe recipe = new Recipe(addRecipeRequest);
		when(recipeManagementService.addRecipe(addRecipeRequest)).thenReturn(recipe);

		ResponseEntity<RecipeResponse> responseEntity = recipeManagementController.addRecipe(addRecipeRequest);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateRecipe_Success() {
		UpdateRecipeRequest updateRecipeRequest = new UpdateRecipeRequest(1, "Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe updatedRecipe = new Recipe(updateRecipeRequest);
		when(recipeManagementService.updateRecipe(updateRecipeRequest)).thenReturn(updatedRecipe);

		ResponseEntity<Recipe> responseEntity = recipeManagementController.updateRecipe(updateRecipeRequest);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test(expected = CustomRuntimeException.class)
	public void testUpdateRecipe_InvalidRequest() {
		UpdateRecipeRequest updateRecipeRequest = new UpdateRecipeRequest(1, "Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		updateRecipeRequest.setRecipeID(0); // Invalid ID

		recipeManagementController.updateRecipe(updateRecipeRequest);

	}

	@Test
	public void testDeleteRecipe_Success() {
		int id = 1;
		String message = "Recipe deleted successfully";
		when(recipeManagementService.deleteRecipe(id)).thenReturn(message);

		ResponseEntity<String> responseEntity = recipeManagementController.deleteRecipe(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody());
	}

	@Test
	public void testFilterRecipes_Success() {
		String isVeg = "Y";
		List<Recipe> filteredRecipes = new ArrayList<>();
		when(recipeManagementService.filterRecipesBasedOnIsVeg(isVeg)).thenReturn(filteredRecipes);

		ResponseEntity<List<Recipe>> responseEntity = recipeManagementController.filterRecipes(isVeg);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(filteredRecipes, responseEntity.getBody());
	}

	@Test
	public void testFindRecipesByNoOfServingsAndIngredients_Success() {
		String noOfServings = "2";
		Set<String> ingredients = new HashSet<>();
		ingredients.add("egg");
		ingredients.add("milk");
		List<Recipe> foundRecipes = new ArrayList<>();
		when(recipeManagementService.findRecipesByNoOfServingsAndIngredients(noOfServings, ingredients))
				.thenReturn(foundRecipes);

		ResponseEntity<List<Recipe>> responseEntity = recipeManagementController
				.findRecipesByNoOfServingsAndIngredients(noOfServings, ingredients);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(foundRecipes, responseEntity.getBody());

	}

	@Test
	public void testGetRecipesWithoutIngredientAndWithKeyword_Success() {
		Set<String> ingredients = new HashSet<>();
		ingredients.add("ingredient1");
		ingredients.add("ingredient2");
		String keyword = "keyword";
		List<Recipe> foundRecipes = new ArrayList<>();

		when(recipeManagementService.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword))
				.thenReturn(foundRecipes);

		ResponseEntity<List<Recipe>> responseEntity = recipeManagementController
				.getRecipesWithoutIngredientAndWithKeyword(ingredients, keyword);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(foundRecipes, responseEntity.getBody());
	}

}
