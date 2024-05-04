package com.cgi.recipe.management.system.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRecipeRequest {

	
	@NotNull(message = "cannot be null")
	private int recipeID;
	
	private String dishName;
	
	private String isVeg;
	
	private String noOfServings;
	
	private List<String> ingredients;
	
	private String instructions;
	
}
