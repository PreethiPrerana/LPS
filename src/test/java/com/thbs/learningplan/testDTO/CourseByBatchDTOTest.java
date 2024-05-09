package com.thbs.learningplan.testDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.CourseDTO;

import java.util.ArrayList;
import java.util.List;


class CourseByBatchDTOTest {

    @Test
    void testGetterAndSetter() {
        // Arrange
        CourseByBatchDTO courseByBatchDTO = new CourseByBatchDTO();
        Long batchId = 1L;
        List<CourseDTO> courses = new ArrayList<>();

        // Act
        courseByBatchDTO.setBatchId(batchId);
        courseByBatchDTO.setCourses(courses);

        // Assert
        Assertions.assertEquals(batchId, courseByBatchDTO.getBatchId());
        Assertions.assertEquals(courses, courseByBatchDTO.getCourses());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long batchId = 1L;
        List<CourseDTO> courses = new ArrayList<>();

        // Act
        CourseByBatchDTO courseByBatchDTO = new CourseByBatchDTO(batchId, courses);

        // Assert
        Assertions.assertEquals(batchId, courseByBatchDTO.getBatchId());
        Assertions.assertEquals(courses, courseByBatchDTO.getCourses());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        CourseByBatchDTO courseByBatchDTO = new CourseByBatchDTO();

        // Assert
        Assertions.assertNull(courseByBatchDTO.getBatchId());
        Assertions.assertNull(courseByBatchDTO.getCourses());
    }
}
