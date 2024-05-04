package com.cgi.recipe.management.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.response.RecipeResponse;
import com.cgi.recipe.management.system.service.RecipeManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class RecipeManagementController {

	@Autowired
	RecipeManagementService recipeManagementService;

	@GetMapping("fetch")
	public List<RecipeResponse> getrecipes() {
		List<Recipe> recipes = recipeManagementService.getAllRecipes();
		List<RecipeResponse> recipeReponseList = new ArrayList();
		recipes.stream().forEach(recipe -> {
			recipeReponseList.add(new RecipeResponse(recipe));
		});
		return recipeReponseList;
	}

	@PostMapping("add")
	public RecipeResponse addrecipe(@RequestBody AddRecipeRequest addRecipeRequest) {
		Recipe recipe = recipeManagementService.addRecipe(addRecipeRequest);
		return new RecipeResponse(recipe);
	}

	@PutMapping("update")
	public Recipe updaterecipe(@Valid @RequestBody UpdateRecipeRequest updateRecipeRequest) {
		Recipe recipe = recipeManagementService.updateRecipe(updateRecipeRequest);
		return recipe;
	}

	// @pathvariable
	@DeleteMapping("delete")
	public String deleterecipe(@RequestParam int id) {
		return recipeManagementService.deleteRecipe(id);
	}

	@GetMapping("filter/{isVeg}")
	public List<Recipe> filterRecipes(@PathVariable("isVeg") String isVeg) {
		return recipeManagementService.filterRecipesBasedOnIsVeg(isVeg);
	}

	@GetMapping("/servings/{noOfServings}/withIngredient/{ingredients1}")
	public List<Recipe> findRecipesByNoOfServingsAndIngredients(@PathVariable("noOfServings") String noOfServings,
			@PathVariable("ingredients1") Set<String> ingredients1) {
		return recipeManagementService.findRecipesByNoOfServingsAndIngredients(noOfServings, ingredients1);
	}

	@GetMapping("/withoutIngredient/{ingredient}/withInstructionKeyword/{keyword}")
	public List<Recipe> getRecipesWithoutIngredientAndWithKeyword(@PathVariable("ingredient") Set<String> ingredients,
			@PathVariable("keyword") String keyword) {
		return recipeManagementService.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword);
	}
	

}
