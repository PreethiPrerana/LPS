package com.thbs.learningplan.testDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thbs.learningplan.dto.BatchLearningPlanDTO;
import com.thbs.learningplan.model.BatchCourse;

import java.util.ArrayList;
import java.util.List;

public class BatchLearningPlanDTOTest {

    @Test
    public void testGetterAndSetter() {
        // Arrange
        BatchLearningPlanDTO batchLearningPlanDTO = new BatchLearningPlanDTO();
        Long learningPlanId = 1L;
        String learningPlanName = "Sample Learning Plan";
        String type = "Type A";
        Long batchId = 100L;
        List<BatchCourse> batchCourses = new ArrayList<>();

        // Act
        batchLearningPlanDTO.setLearningPlanId(learningPlanId);
        batchLearningPlanDTO.setLearningPlanName(learningPlanName);
        batchLearningPlanDTO.setType(type);
        batchLearningPlanDTO.setBatchId(batchId);
        batchLearningPlanDTO.setBatchCourses(batchCourses);

        // Assert
        Assertions.assertEquals(learningPlanId, batchLearningPlanDTO.getLearningPlanId());
        Assertions.assertEquals(learningPlanName, batchLearningPlanDTO.getLearningPlanName());
        Assertions.assertEquals(type, batchLearningPlanDTO.getType());
        Assertions.assertEquals(batchId, batchLearningPlanDTO.getBatchId());
        Assertions.assertEquals(batchCourses, batchLearningPlanDTO.getBatchCourses());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        Long learningPlanId = 1L;
        String learningPlanName = "Sample Learning Plan";
        String type = "Type A";
        Long batchId = 100L;
        List<BatchCourse> batchCourses = new ArrayList<>();

        // Act
        BatchLearningPlanDTO batchLearningPlanDTO = new BatchLearningPlanDTO(learningPlanId, learningPlanName, type, batchId, batchCourses);

        // Assert
        Assertions.assertEquals(learningPlanId, batchLearningPlanDTO.getLearningPlanId());
        Assertions.assertEquals(learningPlanName, batchLearningPlanDTO.getLearningPlanName());
        Assertions.assertEquals(type, batchLearningPlanDTO.getType());
        Assertions.assertEquals(batchId, batchLearningPlanDTO.getBatchId());
        Assertions.assertEquals(batchCourses, batchLearningPlanDTO.getBatchCourses());
    }
}
