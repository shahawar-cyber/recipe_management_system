package com.cgi.recipe.management.system.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.repository.RecipeManagementRepository;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.service.impl.RecipeManagementServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RecipeManagementServiceImplTests {

	@InjectMocks
	private RecipeManagementServiceImpl recipeManagementService;

	@Mock
	private RecipeManagementRepository recipeManagementRepository;

	private List<Recipe> mockRecipeList;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockRecipeList = new ArrayList<>();

		AddRecipeRequest addRecipeRequest = new AddRecipeRequest();
		addRecipeRequest.setDishName("Scrambled Egg");
		addRecipeRequest.setIngredients(Stream.of("eggs", "pan").collect(Collectors.toSet()));
		addRecipeRequest.setIsVeg("N");
		addRecipeRequest.setInstructions("put the egg on a pan. Please keep the pan on the stove");
		addRecipeRequest.setNoOfServings("5");

		AddRecipeRequest addRecipeRequest1 = new AddRecipeRequest();
		addRecipeRequest1.setDishName("Palak Paneer");
		addRecipeRequest1.setIngredients(Stream.of("onions", "spinach", "paneer").collect(Collectors.toSet()));
		addRecipeRequest1.setIsVeg("Y");
		addRecipeRequest1.setInstructions("Wash Palak and place it in a pan, Simmer for 5min");
		addRecipeRequest1.setNoOfServings("5");

		mockRecipeList.add(new Recipe(addRecipeRequest));
		mockRecipeList.add(new Recipe(addRecipeRequest1));
		// Mock the behavior of the repository
		when(recipeManagementRepository.findAll()).thenReturn(mockRecipeList);
	}

	@Test
	public void testGetAllRecipes() {

		List<Recipe> result = recipeManagementService.getAllRecipes();
		verify(recipeManagementRepository).findAll();
		assertEquals(mockRecipeList, result);

	}

}
