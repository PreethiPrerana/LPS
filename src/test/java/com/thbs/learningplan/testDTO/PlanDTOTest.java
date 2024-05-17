package com.thbs.learningplan.testDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.PlanDTO;

import java.util.ArrayList;
import java.util.List;

class PlanDTOTest {

    @Test
    void testGetterAndSetter() {
        // Arrange
        PlanDTO planDTO = new PlanDTO();
        Long batchId = 1L;
        Long learningPlanId = 100L;
        String learningPlanName = "Sample Learning Plan";
        String learningPlanType = "Type A";
        List<BatchCourseDTO> batchCourses = new ArrayList<>();

        // Act
        planDTO.setBatchId(batchId);
        planDTO.setLearningPlanId(learningPlanId);
        planDTO.setLearningPlanName(learningPlanName);
        planDTO.setLearningPlanType(learningPlanType);
        planDTO.setBatchCourses(batchCourses);

        // Assert
        Assertions.assertEquals(batchId, planDTO.getBatchId());
        Assertions.assertEquals(learningPlanId, planDTO.getLearningPlanId());
        Assertions.assertEquals(learningPlanName, planDTO.getLearningPlanName());
        Assertions.assertEquals(learningPlanType, planDTO.getLearningPlanType());
        Assertions.assertEquals(batchCourses, planDTO.getBatchCourses());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long batchId = 1L;
        Long learningPlanId = 100L;
        String learningPlanName = "Sample Learning Plan";
        String learningPlanType = "Type A";
        List<BatchCourseDTO> batchCourses = new ArrayList<>();

        // Act
        PlanDTO planDTO = new PlanDTO(batchId, learningPlanId, learningPlanName, learningPlanType, batchCourses);

        // Assert
        Assertions.assertEquals(batchId, planDTO.getBatchId());
        Assertions.assertEquals(learningPlanId, planDTO.getLearningPlanId());
        Assertions.assertEquals(learningPlanName, planDTO.getLearningPlanName());
        Assertions.assertEquals(learningPlanType, planDTO.getLearningPlanType());
        Assertions.assertEquals(batchCourses, planDTO.getBatchCourses());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        PlanDTO planDTO = new PlanDTO();

        // Assert
        Assertions.assertNull(planDTO.getBatchId());
        Assertions.assertNull(planDTO.getLearningPlanId());
        Assertions.assertNull(planDTO.getLearningPlanName());
        Assertions.assertNull(planDTO.getLearningPlanType());
        Assertions.assertNull(planDTO.getBatchCourses());
    }
}
