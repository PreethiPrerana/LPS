package com.thbs.learningplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.thbs.learningplan")
public class LearningplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningplanApplication.class, args);
	}

}
