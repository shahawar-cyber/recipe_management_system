package com.cgi.recipe.management.system.service;

import java.util.List;
import java.util.Set;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;

import jakarta.validation.Valid;

public interface RecipeManagementService {

	List<Recipe> getAllRecipes();

	Recipe addRecipe(AddRecipeRequest addRecipeRequest);

	Recipe updateRecipe(@Valid UpdateRecipeRequest updateRecipeRequest);

	String deleteRecipe(int id);

	List<Recipe> filterRecipesBasedOnIsVeg(String isVeg);
	
	List<Recipe> findRecipesByNoOfServingsAndIngredients(String noOfServings, Set<String> ingredients);

	List<Recipe> findByIngredientsNotContainingAndInstructionsContaining(Set<String> ingredient, String keyword);

}
