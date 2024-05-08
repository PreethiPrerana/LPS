package com.thbs.learningplan;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningplanApplicationTests {
	@Autowired
	private LearningplanApplication application;

	@Test
	void contextLoads() {
		assertNotNull(application);
	}
}
