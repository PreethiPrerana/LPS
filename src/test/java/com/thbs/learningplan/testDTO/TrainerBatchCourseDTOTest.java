package com.thbs.learningplan.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.thbs.learningplan.dto.TrainerBatchCourseDTO;

class TrainerBatchCourseDTOTest {
    
    @Test
    void testNoArgsConstructor() {
        TrainerBatchCourseDTO dto = new TrainerBatchCourseDTO();
        assertNotNull(dto);
    }

    @Test
    void testAllArgsConstructor() {
        Long batchId = 1L;
        Long trainerId = 2L;
        String trainer = "John Doe";
        Long courseId = 3L;
        String courseName = "Java Basics";
        Date startDate = new Date();
        Date endDate = new Date();

        TrainerBatchCourseDTO dto = new TrainerBatchCourseDTO(batchId, trainerId, trainer, courseId, courseName,
                startDate, endDate);

        assertEquals(batchId, dto.getBatchId());
        assertEquals(trainerId, dto.getTrainerId());
        assertEquals(trainer, dto.getTrainer());
        assertEquals(courseId, dto.getCourseId());
        assertEquals(courseName, dto.getCourseName());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
    }

    @Test
    void testGettersAndSetters() {
        Long batchId = 1L;
        Long trainerId = 2L;
        String trainer = "John Doe";
        Long courseId = 3L;
        String courseName = "Java Basics";
        Date startDate = new Date();
        Date endDate = new Date();

        TrainerBatchCourseDTO dto = new TrainerBatchCourseDTO();
        dto.setBatchId(batchId);
        dto.setTrainerId(trainerId);
        dto.setTrainer(trainer);
        dto.setCourseId(courseId);
        dto.setCourseName(courseName);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        assertEquals(batchId, dto.getBatchId());
        assertEquals(trainerId, dto.getTrainerId());
        assertEquals(trainer, dto.getTrainer());
        assertEquals(courseId, dto.getCourseId());
        assertEquals(courseName, dto.getCourseName());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
    }
}
