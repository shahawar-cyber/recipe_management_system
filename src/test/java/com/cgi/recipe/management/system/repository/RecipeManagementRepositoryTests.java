package com.cgi.recipe.management.system.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.request.AddRecipeRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RecipeManagementRepositoryTests {

	@Autowired
	private RecipeManagementRepository recipeManagementRepository;

	@Test
	public void testFindByIsVeg() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		AddRecipeRequest addRecipeRequest2 = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe recipe1 = new Recipe(addRecipeRequest);
		Recipe recipe2 = new Recipe(addRecipeRequest2);

		List<Recipe> vegRecipes = recipeManagementRepository.findByIsVeg("Y");

		assertEquals(2, vegRecipes.size());
	}

	@Test
	public void testFindRecipeByNoOfServingsAndIngredients() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		AddRecipeRequest addRecipeRequest2 = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");

		Recipe recipe1 = recipeManagementRepository.save(new Recipe(addRecipeRequest));
		Recipe recipe2 = recipeManagementRepository.save(new Recipe(addRecipeRequest2));

		String noOfServings = "2";
		Set<String> ingredients = new HashSet<>();
		ingredients.add("eggs");
		ingredients.add("pan");

		List<Recipe> foundRecipes = recipeManagementRepository.findRecipeByNoOfServingsAndIngredients(noOfServings,
				ingredients);

		assertEquals(2, foundRecipes.size());
		assertTrue(foundRecipes.contains(recipe1));
		assertTrue(foundRecipes.contains(recipe2));

	}

	@Test
	public void testFindByIngredientsNotContainingAndInstructionsContaining() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		AddRecipeRequest addRecipeRequest2 = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");

		Recipe recipe1 = recipeManagementRepository.save(new Recipe(addRecipeRequest));
		Recipe recipe2 = recipeManagementRepository.save(new Recipe(addRecipeRequest2));

		Set<String> ingredients = new HashSet<>();
		ingredients.add("eggs");
		ingredients.add("pan");
		String keyword = "keyword";

		List<Recipe> foundRecipes = recipeManagementRepository
				.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword);

		assertEquals(0, foundRecipes.size()); // Assuming no recipes match the criteria
		assertFalse(foundRecipes.contains(recipe1)); // Assuming recipe1 doesn't match the criteria
		assertFalse(foundRecipes.contains(recipe2));

	}
}
