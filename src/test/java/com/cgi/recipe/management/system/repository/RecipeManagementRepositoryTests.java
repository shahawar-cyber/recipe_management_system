package com.cgi.recipe.management.system.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cgi.recipe.management.system.initializer.DataInitializer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@ExtendWith(SpringExtension.class)
public class RecipeManagementRepositoryTests {

	@Autowired
	private RecipeManagementRepository recipeManagementRepository;

	@Autowired
	DataInitializer dataInitializer;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;

//    @Test
//    @Transactional
//    public void testSchemaCreation() {
//        // Create the schema using EntityManager
//        entityManager.createNativeQuery("CREATE SCHEMA IF NOT EXISTS RECIPE_MANAGEMENT_SYSTEM").executeUpdate();
//
//        // Check if the schema exists by querying information_schema
//        Long count = (Long) entityManager.createNativeQuery(
//                "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'RECIPE_MANAGEMENT_SYSTEM'")
//                .getSingleResult();
//
//        // Assert that the schema is created
//        assertThat(count).isEqualTo(1L);
//    }

	@Before
	public void prepare() {
		MockitoAnnotations.initMocks(this);
		this.dataInitializer.init(); // your Injected bean
	}

	@Test
	public void givenRecipieObject_whenSave_thenReturnSavedRecipe() {

//		AddRecipeRequest addRecipeRequest = new AddRecipeRequest();
//		addRecipeRequest.setDishName("Scrambled Egg");
//		addRecipeRequest.setIngredients(Stream.of("eggs", "pan").collect(Collectors.toSet()));
//		addRecipeRequest.setIsVeg("N");
//		addRecipeRequest.setInstructions("put the egg on a pan. Please keep the pan on the stove");
//		addRecipeRequest.setNoOfServings("5");
//		Recipe recipe = new Recipe(addRecipeRequest);
//
//		Recipe savedRecipie = recipeManagementRepository.save(recipe);
//
//		Assertions.assertThat(savedRecipie).isNotNull();
//		Assertions.assertThat(savedRecipie.getRecipeID()).isGreaterThan(0);
	}

}
