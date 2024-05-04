package com.cgi.recipe.management.system.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.repository.RecipeManagementRepository;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.service.impl.RecipeManagementServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RecipeManagementServiceImplTests {

	@Mock
	private RecipeManagementRepository recipeManagementRepository;

	@InjectMocks
	private RecipeManagementServiceImpl recipeManagementService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		mockRecipeList = new ArrayList<>();
//
//		AddRecipeRequest addRecipeRequest = new AddRecipeRequest();
//		addRecipeRequest.setDishName("Scrambled Egg");
//		addRecipeRequest.setIngredients(Stream.of("eggs", "pan").collect(Collectors.toSet()));
//		addRecipeRequest.setIsVeg("N");
//		addRecipeRequest.setInstructions("put the egg on a pan. Please keep the pan on the stove");
//		addRecipeRequest.setNoOfServings("5");
//
//		AddRecipeRequest addRecipeRequest1 = new AddRecipeRequest();
//		addRecipeRequest1.setDishName("Palak Paneer");
//		addRecipeRequest1.setIngredients(Stream.of("onions", "spinach", "paneer").collect(Collectors.toSet()));
//		addRecipeRequest1.setIsVeg("Y");
//		addRecipeRequest1.setInstructions("Wash Palak and place it in a pan, Simmer for 5min");
//		addRecipeRequest1.setNoOfServings("5");
//
//		mockRecipeList.add(new Recipe(addRecipeRequest));
//		mockRecipeList.add(new Recipe(addRecipeRequest1));
//		// Mock the behavior of the repository
//		when(recipeManagementRepository.findAll()).thenReturn(mockRecipeList);
//	}

	@Test
	public void testGetAllRecipes() {
		List<Recipe> recipes = new ArrayList<>();
		when(recipeManagementRepository.findAll()).thenReturn(recipes);

		List<Recipe> result = recipeManagementService.getAllRecipes();

		assertEquals(recipes, result);

	}

	@Test
	public void testAddRecipe() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe savedRecipe = new Recipe(addRecipeRequest);
		when(recipeManagementRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

		Recipe result = recipeManagementService.addRecipe(addRecipeRequest);

		assertEquals(savedRecipe, result);
	}

	@Test
	public void testUpdateRecipe() {
		UpdateRecipeRequest updateRecipeRequest = new UpdateRecipeRequest(2, "Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe existingRecipe = new Recipe(updateRecipeRequest);
		Optional<Recipe> optionalRecipe = Optional.of(existingRecipe);

		when(recipeManagementRepository.findById(updateRecipeRequest.getRecipeID())).thenReturn(optionalRecipe);
		when(recipeManagementRepository.save(any(Recipe.class))).thenReturn(existingRecipe);
		
		Recipe result = recipeManagementService.updateRecipe(updateRecipeRequest);

		assertEquals(updateRecipeRequest.getDishName(), result.getDishName());
		assertEquals(updateRecipeRequest.getIsVeg(), result.getIsVeg());
		assertEquals(updateRecipeRequest.getNoOfServings(), result.getNoOfServings());
		assertEquals(updateRecipeRequest.getIngredients(), result.getIngredients());
		assertEquals(updateRecipeRequest.getInstructions(), result.getInstructions());
	}

	@Test
	public void testDeleteRecipe() {
		int id = 1;
		String result = recipeManagementService.deleteRecipe(id);

		assertEquals("Recipe deleted successfully", result);
		verify(recipeManagementRepository).deleteById(id);
	}

	@Test
	public void testFilterRecipesBasedOnIsVeg() {
		String isVeg = "Y";
		List<Recipe> filteredRecipes = new ArrayList<>();
		when(recipeManagementRepository.findByIsVeg(isVeg)).thenReturn(filteredRecipes);

		List<Recipe> result = recipeManagementService.filterRecipesBasedOnIsVeg(isVeg);

		assertEquals(filteredRecipes, result);
	}

	@Test
	public void testFindRecipesByNoOfServingsAndIngredients() {
		String noOfServings = "2";
		Set<String> ingredients = new HashSet<>();
		ingredients.add("egg");
		ingredients.add("milk");

		Set<String> lowerCaseIngredients = new HashSet<>();
		lowerCaseIngredients.add("egg");
		lowerCaseIngredients.add("milk");
		List<Recipe> foundRecipes = new ArrayList<>();
		when(recipeManagementRepository.findRecipeByNoOfServingsAndIngredients(noOfServings, lowerCaseIngredients))
				.thenReturn(foundRecipes);

		List<Recipe> result = recipeManagementService.findRecipesByNoOfServingsAndIngredients(noOfServings,
				ingredients);

		assertEquals(foundRecipes, result);
	}

	@Test
	public void testFindByIngredientsNotContainingAndInstructionsContaining() {
		Set<String> ingredients = new HashSet<>();
		ingredients.add("egg");
		ingredients.add("milk");
		String keyword = "pan";

		Set<String> lowerCaseIngredients = new HashSet<>();
		lowerCaseIngredients.add("ingredient1");
		lowerCaseIngredients.add("ingredient2");
		List<Recipe> foundRecipes = new ArrayList<>();
		when(recipeManagementRepository.findByIngredientsNotContainingAndInstructionsContaining(lowerCaseIngredients,
				keyword)).thenReturn(foundRecipes);

		List<Recipe> result = recipeManagementService
				.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword);

		assertEquals(foundRecipes, result);
	}

}
