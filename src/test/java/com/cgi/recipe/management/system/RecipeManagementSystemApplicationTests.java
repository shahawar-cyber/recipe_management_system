package com.cgi.recipe.management.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeManagementSystemApplicationTests {

	@Value("${test-property}")
	private String testProperty;

	@Test
	void contextLoads() {
		//assertEquals("test-value", testProperty);

	}

}
