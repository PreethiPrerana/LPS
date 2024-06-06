package com.thbs.learningplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.thbs.learningplan")
@EnableDiscoveryClient
/* NEW MODIFIED LEARNING PLAN SERVICE CODE */
public class LearningplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningplanApplication.class, args);
	}

}
