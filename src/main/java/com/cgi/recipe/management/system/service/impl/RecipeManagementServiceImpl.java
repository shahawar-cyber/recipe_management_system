package com.cgi.recipe.management.system.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.repository.RecipeManagementRepository;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.service.RecipeManagementService;

import jakarta.validation.Valid;

@Service
public class RecipeManagementServiceImpl implements RecipeManagementService {

	@Autowired
	RecipeManagementRepository recipeManagementRepository;

	@Override
	public List<Recipe> getAllRecipes() {
		return recipeManagementRepository.findAll();
	}

	@Override
	public Recipe addRecipe(AddRecipeRequest addRecipeRequest) {
		Recipe recipe = new Recipe(addRecipeRequest);
		recipe = recipeManagementRepository.save(recipe);
		return recipe;
	}

	@Override
	public Recipe updateRecipe(@Valid UpdateRecipeRequest updateRecipeRequest) {
		Recipe recipe = recipeManagementRepository.findById(updateRecipeRequest.getRecipeID()).get();
		if (!StringUtils.isEmpty(updateRecipeRequest.getDishName())) {
			recipe.setDishName(updateRecipeRequest.getDishName());
		}
		recipe = recipeManagementRepository.save(recipe);
		return recipe;
	}

	@Override
	public String deleteRecipe(int id) {
		recipeManagementRepository.deleteById(id);
		return "success";
	}

	@Override
	public List<Recipe> filterRecipesBasedOnIsVeg(String isVeg) {
		return recipeManagementRepository.findByIsVeg(isVeg);
	}

	@Override
	public List<Recipe> findRecipesByNoOfServingsAndIngredients(String noOfServings, Set<String> ingredients) {
		Set<String> lowerCaseIngredients = convertToLowerCase(ingredients);
		return recipeManagementRepository.findRecipeByNoOfServingsAndIngredients(noOfServings, lowerCaseIngredients);
	}

	@Override
	public List<Recipe> findByIngredientsNotContainingAndInstructionsContaining(Set<String> ingredients,
			String keyword) {
		Set<String> lowerCaseIngredients = convertToLowerCase(ingredients);
		return recipeManagementRepository.findByIngredientsNotContainingAndInstructionsContaining(lowerCaseIngredients,
				keyword);
	}
	
	
	private Set<String> convertToLowerCase(Set<String> ingredients) {
		return ingredients.stream().map(String::toLowerCase).collect(Collectors.toSet());
	}

}
