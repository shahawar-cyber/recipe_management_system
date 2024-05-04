package com.cgi.recipe.management.system.request;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateRecipeRequest {

	@NotNull(message = "cannot be null")
	private int recipeID;

	private String dishName;

	private String isVeg;

	private String noOfServings;

	private Set<String> ingredients;

	private String instructions;

}
