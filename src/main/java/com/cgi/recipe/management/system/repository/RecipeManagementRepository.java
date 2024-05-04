package com.cgi.recipe.management.system.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cgi.recipe.management.system.entity.Recipe;

@Repository
public interface RecipeManagementRepository extends JpaRepository<Recipe, Integer> {

	List<Recipe> findByIsVeg(String isVeg);
	
	@Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE r.noOfServings = :noOfServings AND LOWER(i) IN (:ingredients)")
	List<Recipe> findRecipeByNoOfServingsAndIngredients(String noOfServings, Set<String> ingredients);

	@Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(r.instructions) LIKE LOWER(concat('%', :keyword, '%')) AND LOWER(i) NOT IN :ingredients")
	List<Recipe> findByIngredientsNotContainingAndInstructionsContaining(Set<String> ingredients, String keyword);

}
