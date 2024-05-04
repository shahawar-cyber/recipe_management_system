package com.cgi.recipe.management.system.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddRecipeRequest {
	
	private String dishName;
	
	@JsonProperty("isVeg")
	private String isVeg;
	
	private String noOfServings;
	
	private Set<String> ingredients;
	
	private String instructions;

}
