package com.thbs.learningplan.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;

@SpringBootTest
class BatchCourseIdTest {

    @Test
    void testGettersAndSetters() {
        // Initialize a BatchCourseId object
        BatchCourseId batchCourseId = new BatchCourseId();

        // Set values using setters
        Long batchId = 1L;
        LearningPlan learningPlan = new LearningPlan();
        Course course = new Course();

        batchCourseId.setBatchId(batchId);
        batchCourseId.setLearningPlan(learningPlan);
        batchCourseId.setCourse(course);

        // Test getters
        assertEquals(batchId, batchCourseId.getBatchId());
        assertEquals(learningPlan, batchCourseId.getLearningPlan());
        assertEquals(course, batchCourseId.getCourse());
    }

    @Test
    void testNoArgsConstructor() {
        // Initialize a BatchCourseId object using no-args constructor
        BatchCourseId batchCourseId = new BatchCourseId();

        // Test getters
        assertNull(batchCourseId.getBatchId());
        assertNull(batchCourseId.getLearningPlan());
        assertNull(batchCourseId.getCourse());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a BatchCourseId object using all-args constructor
        Long batchId = 1L;
        LearningPlan learningPlan = new LearningPlan();
        Course course = new Course();

        BatchCourseId batchCourseId = new BatchCourseId(batchId, learningPlan, course);

        // Test getters
        assertEquals(batchId, batchCourseId.getBatchId());
        assertEquals(learningPlan, batchCourseId.getLearningPlan());
        assertEquals(course, batchCourseId.getCourse());
    }
}
