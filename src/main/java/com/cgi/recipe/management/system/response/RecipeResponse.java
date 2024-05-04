package com.cgi.recipe.management.system.response;

import java.util.Set;

import com.cgi.recipe.management.system.entity.Recipe;

import lombok.Data;

// chnage to java 17
@Data
public class RecipeResponse {

	private String dishName;
	private String isVeg;
	private String noOfServings;
	private Set<String> ingredients;
	private String instructions;

	public RecipeResponse(Recipe recipe) {
		this.dishName = recipe.getDishName();
		this.isVeg = recipe.getIsVeg();
		this.noOfServings = recipe.getNoOfServings();
		this.ingredients = recipe.getIngredients();
		this.instructions = recipe.getInstructions();

	}
}
