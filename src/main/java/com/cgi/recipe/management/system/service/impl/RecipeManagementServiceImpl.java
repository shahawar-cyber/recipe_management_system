package com.cgi.recipe.management.system.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.exception.CustomRuntimeException;
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
		Recipe recipe = recipeManagementRepository.findById(updateRecipeRequest.getRecipeID())
				.orElseThrow(() -> new CustomRuntimeException("Failed to process request", "Recipe not found",
						HttpStatus.INTERNAL_SERVER_ERROR));

		Map<String, Object> updateFields = new HashMap<>();
		updateFields.put("dishName", updateRecipeRequest.getDishName());
		updateFields.put("isVeg", updateRecipeRequest.getIsVeg());
		updateFields.put("noOfServings", updateRecipeRequest.getNoOfServings());
		updateFields.put("ingredients", updateRecipeRequest.getIngredients());
		updateFields.put("instructions", updateRecipeRequest.getInstructions());

		updateFields(recipe, updateFields);

		recipe = recipeManagementRepository.save(recipe);
		return recipe;
	}

	private void updateFields(Recipe recipe, Map<String, Object> updateFields) {
		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();

			if (value != null && StringUtils.hasText(value.toString())) {
				try {
					Field field = Recipe.class.getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(recipe, value);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					throw new CustomRuntimeException("Failed to process request", "No such field found during updation",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
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
