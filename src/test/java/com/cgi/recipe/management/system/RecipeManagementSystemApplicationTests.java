package com.cgi.recipe.management.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeManagementSystemApplicationTests {

	@Value("${test-property}")
	private String testProperty;

	@Test
	void contextLoads() {
		assertEquals("test-value", testProperty);

	}

}
