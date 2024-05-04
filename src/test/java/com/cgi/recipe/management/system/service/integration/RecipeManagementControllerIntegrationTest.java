package com.cgi.recipe.management.system.service.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cgi.recipe.management.system.controller.RecipeManagementController;
import com.cgi.recipe.management.system.entity.Recipe;
import com.cgi.recipe.management.system.request.AddRecipeRequest;
import com.cgi.recipe.management.system.request.UpdateRecipeRequest;
import com.cgi.recipe.management.system.service.RecipeManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
public class RecipeManagementControllerIntegrationTest {

	private MockMvc mockMvc;

	@Mock
	private RecipeManagementService recipeManagementService;

	@InjectMocks
	private RecipeManagementController recipeManagementController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeManagementController).build();
	}

	@Test
	public void testAddRecipe() throws Exception {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5", Set.of("eggs", "pan"),
				"put the egg on a pan. Please keep the pan on the stove");

		Recipe recipe = new Recipe(addRecipeRequest);

		when(recipeManagementService.addRecipe(addRecipeRequest)).thenReturn(recipe);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/recipe/add").contentType(MediaType.APPLICATION_JSON).content(
						"{\"dishName\":\"Scrambled Egg\",\"isVeg\":\"N\",\"noOfServings\":\"5\",\"ingredients\":[\"eggs\",\"pan\"],\"instructions\":\"put the egg on a pan. Please keep the pan on the stove\"}")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.dishName").value("Scrambled Egg"))
				.andExpect(jsonPath("$.isVeg").value("N")).andReturn();
	}

	@Test
	public void testGetRecipes() throws Exception {
		List<Recipe> recipes = new ArrayList<>();
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5", Set.of("eggs", "pan"),
				"put the egg on a pan. Please keep the pan on the stove");
		recipes.add(new Recipe(addRecipeRequest));

		when(recipeManagementService.getAllRecipes()).thenReturn(recipes);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/recipe/fetch").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();

	}

	@Test
	public void testUpdateRecipe() throws Exception {
		UpdateRecipeRequest updateRecipeRequest = new UpdateRecipeRequest(1, "Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");
		Recipe updatedRecipe = new Recipe(updateRecipeRequest);
		when(recipeManagementService.updateRecipe(updateRecipeRequest)).thenReturn(updatedRecipe);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequest = objectMapper.writeValueAsString(updateRecipeRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/recipe/update")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest.getBytes())).andExpect(status().isOk())
				.andReturn();

	}

	@Test
	public void testDeleteRecipe() throws Exception {
		int id = 1;
		String message = "Recipe deleted successfully";
		when(recipeManagementService.deleteRecipe(id)).thenReturn(message);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/recipe/delete").param("id", String.valueOf(id)))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testFilterRecipes() throws Exception {
		String isVeg = "Y";
		List<Recipe> filteredRecipes = new ArrayList<>();
		when(recipeManagementService.filterRecipesBasedOnIsVeg(isVeg)).thenReturn(filteredRecipes);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/recipe/filter/{isVeg}", isVeg).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testFindRecipesByNoOfServingsAndIngredients() throws Exception {
		String noOfServings = "2";
		Set<String> ingredients = Set.of("egg", "milk");
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");

		List<Recipe> foundRecipes = List.of(new Recipe(addRecipeRequest));
		when(recipeManagementService.findRecipesByNoOfServingsAndIngredients(noOfServings, ingredients))
				.thenReturn(foundRecipes);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/recipe/servings/{noOfServings}/withIngredient/{ingredients}",
						noOfServings, String.join(",", ingredients)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testGetRecipesWithoutIngredientAndWithKeyword() throws Exception {
		Set<String> ingredients = Set.of("egg", "milk");
		String keyword = "keyword";
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest("Scrambled Egg", "N", "5",
				Stream.of("eggs", "pan").collect(Collectors.toSet()),
				"put the egg on a pan. Please keep the pan on the stove");

		List<Recipe> foundRecipes = List.of(new Recipe(addRecipeRequest));
		when(recipeManagementService.findByIngredientsNotContainingAndInstructionsContaining(ingredients, keyword))
				.thenReturn(foundRecipes);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/recipe/withoutIngredient/{ingredient}/withInstructionKeyword/{keyword}",
								String.join(",", ingredients), keyword)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

}
