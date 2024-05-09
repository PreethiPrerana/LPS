package com.thbs.learningplan.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;

class LearningPlanTest {

    @Test
    void testGettersAndSetters() {
        // Initialize a LearningPlan object
        LearningPlan learningPlan = new LearningPlan();

        // Set values using setters
        Long learningPlanId = 1L;
        String learningPlanName = "Test Learning Plan";
        String type = "Test Type";
        List<Course> courses = new ArrayList<>();

        learningPlan.setLearningPlanId(learningPlanId);
        learningPlan.setLearningPlanName(learningPlanName);
        learningPlan.setType(type);
        learningPlan.setCourses(courses);

        // Test getters
        assertEquals(learningPlanId, learningPlan.getLearningPlanId());
        assertEquals(learningPlanName, learningPlan.getLearningPlanName());
        assertEquals(type, learningPlan.getType());
        assertEquals(courses, learningPlan.getCourses());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a LearningPlan object using all-args constructor
        Long learningPlanId = 1L;
        String learningPlanName = "Test Learning Plan";
        String type = "Test Type";
        List<Course> courses = new ArrayList<>();

        LearningPlan learningPlan = new LearningPlan(learningPlanId, learningPlanName, type, courses);

        // Test getters
        assertEquals(learningPlanId, learningPlan.getLearningPlanId());
        assertEquals(learningPlanName, learningPlan.getLearningPlanName());
        assertEquals(type, learningPlan.getType());
        assertEquals(courses, learningPlan.getCourses());
    }

   @Test
void testNoArgsConstructor() {
    // Initialize a LearningPlan object using no-args constructor
    LearningPlan learningPlan = new LearningPlan();

    // Test getters
    assertNull(learningPlan.getLearningPlanId());
    assertNull(learningPlan.getLearningPlanName());
    assertNull(learningPlan.getType());
    assertNotNull(learningPlan.getCourses()); // Change assertion to assertNotNull
    assertTrue(learningPlan.getCourses().isEmpty()); // Check if courses list is empty
}

}
