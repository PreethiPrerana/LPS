package com.thbs.learningplan.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;

class BatchCourseTest {

    @Test
    void testGettersAndSetters() {
        // Initialize a BatchCourse object
        BatchCourse batchCourse = new BatchCourse();

        // Set values using setters
        BatchCourseId batchCourseId = new BatchCourseId();
        batchCourseId.setBatchId(1L);
        batchCourseId.setLearningPlan(new LearningPlan());
        batchCourseId.setCourse(new Course());
        Date startDate = new Date();
        Date endDate = new Date();
        String trainer = "Test Trainer";

        batchCourse.setBatchCourseId(batchCourseId);
        batchCourse.setStartDate(startDate);
        batchCourse.setEndDate(endDate);
        batchCourse.setTrainer(trainer);

        // Test getters
        assertEquals(batchCourseId, batchCourse.getBatchCourseId());
        assertEquals(startDate, batchCourse.getStartDate());
        assertEquals(endDate, batchCourse.getEndDate());
        assertEquals(trainer, batchCourse.getTrainer());
    }

    @Test
    void testNoArgsConstructor() {
        // Initialize a BatchCourse object using no-args constructor
        BatchCourse batchCourse = new BatchCourse();

        // Test getters
        assertNull(batchCourse.getBatchCourseId());
        assertNull(batchCourse.getStartDate());
        assertNull(batchCourse.getEndDate());
        assertNull(batchCourse.getTrainer());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a BatchCourse object using all-args constructor
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());
        Date startDate = new Date();
        Date endDate = new Date();
        String trainer = "Test Trainer";

        BatchCourse batchCourse = new BatchCourse(batchCourseId, startDate, endDate, trainer);

        // Test getters
        assertEquals(batchCourseId, batchCourse.getBatchCourseId());
        assertEquals(startDate, batchCourse.getStartDate());
        assertEquals(endDate, batchCourse.getEndDate());
        assertEquals(trainer, batchCourse.getTrainer());
    }
}
