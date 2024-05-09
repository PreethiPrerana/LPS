package com.thbs.learningplan.testDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.TopicDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class BatchCourseDTOTest {

    @Test
    void testGetterAndSetter() {
        // Arrange
        BatchCourseDTO batchCourseDTO = new BatchCourseDTO();
        Date startDate = new Date();
        Date endDate = new Date();
        String trainer = "John Doe";
        String courseName = "Java Programming";
        Long courseId = 1L;
        List<TopicDTO> topics = new ArrayList<>();

        // Act
        batchCourseDTO.setStartDate(startDate);
        batchCourseDTO.setEndDate(endDate);
        batchCourseDTO.setTrainer(trainer);
        batchCourseDTO.setCourseName(courseName);
        batchCourseDTO.setCourseId(courseId);
        batchCourseDTO.setTopic(topics);

        // Assert
        Assertions.assertEquals(startDate, batchCourseDTO.getStartDate());
        Assertions.assertEquals(endDate, batchCourseDTO.getEndDate());
        Assertions.assertEquals(trainer, batchCourseDTO.getTrainer());
        Assertions.assertEquals(courseName, batchCourseDTO.getCourseName());
        Assertions.assertEquals(courseId, batchCourseDTO.getCourseId());
        Assertions.assertEquals(topics, batchCourseDTO.getTopic());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        String trainer = "John Doe";
        String courseName = "Java Programming";
        Long courseId = 1L;
        List<TopicDTO> topics = new ArrayList<>();

        // Act
        BatchCourseDTO batchCourseDTO = new BatchCourseDTO(startDate, endDate, trainer, courseName, courseId, topics);

        // Assert
        Assertions.assertEquals(startDate, batchCourseDTO.getStartDate());
        Assertions.assertEquals(endDate, batchCourseDTO.getEndDate());
        Assertions.assertEquals(trainer, batchCourseDTO.getTrainer());
        Assertions.assertEquals(courseName, batchCourseDTO.getCourseName());
        Assertions.assertEquals(courseId, batchCourseDTO.getCourseId());
        Assertions.assertEquals(topics, batchCourseDTO.getTopic());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        BatchCourseDTO batchCourseDTO = new BatchCourseDTO();

        // Assert
        Assertions.assertNull(batchCourseDTO.getStartDate());
        Assertions.assertNull(batchCourseDTO.getEndDate());
        Assertions.assertNull(batchCourseDTO.getTrainer());
        Assertions.assertNull(batchCourseDTO.getCourseName());
        Assertions.assertNull(batchCourseDTO.getCourseId());
        Assertions.assertNull(batchCourseDTO.getTopic());
    }
}
