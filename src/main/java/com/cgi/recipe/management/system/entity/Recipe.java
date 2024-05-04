package com.cgi.recipe.management.system.entity;

import java.util.Set;

import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "recipe_management_system", name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int recipeID;

	@NotBlank
	@Column(name = "dishName")
	private String dishName;

	@Pattern(regexp = "Y|N", message = "isVeg must be 'Y' or 'N'")
	@Column(name = "isVeg")
	private String isVeg;

	@NotBlank
	@Column(name = "noOfServings")
	private String noOfServings;
	
	@ElementCollection
	@Column(name = "ingredients")
	private Set<String> ingredients;

	@NotBlank
    @Size(max = 1000)
	@Column(name = "instructions")
	private String instructions;

	public Recipe(AddRecipeRequest addRecipeRequest) {
		this.dishName = addRecipeRequest.getDishName();
		this.ingredients = addRecipeRequest.getIngredients();
		this.instructions = addRecipeRequest.getInstructions();
		this.noOfServings = addRecipeRequest.getNoOfServings();
		this.isVeg = addRecipeRequest.getIsVeg();
	}

	public Recipe(UpdateRecipeRequest updateRecipeRequest) {
		this.recipeID = updateRecipeRequest.getRecipeID();
		this.dishName = updateRecipeRequest.getDishName();
		this.ingredients = updateRecipeRequest.getIngredients();
		this.instructions = updateRecipeRequest.getInstructions();
		this.noOfServings = updateRecipeRequest.getNoOfServings();
		this.isVeg = updateRecipeRequest.getIsVeg();
	}

}
