package com.thbs.learningplan.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import com.thbs.learningplan.model.Course;

class CourseTest {
    @Test
    void testGettersAndSetters() {
        // Initialize a Course object
        Course course = new Course();

        // Set values using setters
        Long courseID = 1L;
        String courseName = "Test Course";
        String level = "Intermediate";
        Long courseDuration = 1L;

        course.setCourseId(courseID);
        course.setCourseName(courseName);
        course.setLevel(level);
        course.setCourseDuration(courseDuration);

        // Test getters
        assertEquals(courseID, course.getCourseId());
        assertEquals(courseName, course.getCourseName());
        assertEquals(level, course.getLevel());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialize a Course object using all-args constructor
        Long courseID = 1L;
        String courseName = "Test Course";
        String level = "Intermediate";
        Long courseDuration = 1L;

        Course course = new Course(courseID, courseName, level, courseDuration);

        // Test getters
        assertEquals(courseID, course.getCourseId());
        assertEquals(courseName, course.getCourseName());
        assertEquals(level, course.getLevel());
        assertEquals(courseDuration, course.getCourseDuration());

    }

    @Test
    void testNoArgsConstructor() {
        // Initialize a Course object using no-args constructor
        Course course = new Course();

        // Test getters
        assertNull(course.getCourseId());
        assertNull(course.getCourseName());
        assertNull(course.getLevel());
        assertNull(course.getCourseDuration());
    }
}
