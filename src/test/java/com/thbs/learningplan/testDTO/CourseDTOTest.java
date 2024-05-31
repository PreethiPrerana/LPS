package com.thbs.learningplan.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.thbs.learningplan.dto.CourseDTO;
import com.thbs.learningplan.dto.SubTopicDTO;
import com.thbs.learningplan.dto.TopicDTO;

class CourseDTOTest {

    @Test
    void testGetterAndSetter() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(1L);
        courseDTO.setCourseName("Java");
        List<TopicDTO> topics = new ArrayList<>();
        courseDTO.setTopics(topics);

        assertEquals(1L, courseDTO.getCourseId());
        assertEquals("Java", courseDTO.getCourseName());
        assertEquals(topics, courseDTO.getTopics());
    }

    @Test
    void testAllArgsConstructor() {
        List<SubTopicDTO> subTopics1 = new ArrayList<>();
        subTopics1.add(new SubTopicDTO()); // Assuming SubTopicDTO has a no-args constructor
        List<SubTopicDTO> subTopics2 = new ArrayList<>();
        subTopics2.add(new SubTopicDTO()); // Assuming SubTopicDTO has a no-args constructor

        List<TopicDTO> topics = new ArrayList<>();
        TopicDTO topic1 = new TopicDTO(1L, "Topic 1", subTopics1);
        TopicDTO topic2 = new TopicDTO(2L, "Topic 2", subTopics2);
        topics.add(topic1);
        topics.add(topic2);

        CourseDTO courseDTO = new CourseDTO(1L, "Java", topics);

        assertEquals(1L, courseDTO.getCourseId());
        assertEquals("Java", courseDTO.getCourseName());
        assertEquals(topics, courseDTO.getTopics());
    }

    @Test
    void testNoArgsConstructor() {
        CourseDTO courseDTO = new CourseDTO();

        assertNull(courseDTO.getCourseId());
        assertNull(courseDTO.getCourseName());
        assertNull(courseDTO.getTopics());
    }
}
