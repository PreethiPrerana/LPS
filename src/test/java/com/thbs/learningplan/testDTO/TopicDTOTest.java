package com.thbs.learningplan.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import com.thbs.learningplan.dto.TopicDTO;

class TopicDTOTest {
    @Test
    void testGetterAndSetter() {
        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setTopicId(1L);
        topicDTO.setTopicName("Java");

        assertEquals(1L, topicDTO.getTopicId());
        assertEquals("Java", topicDTO.getTopicName());
    }

    @Test
    void testAllArgsConstructor() {
        TopicDTO topicDTO = new TopicDTO(1L, "Java");

        assertEquals(1L, topicDTO.getTopicId());
        assertEquals("Java", topicDTO.getTopicName());
    }

    @Test
    void testNoArgsConstructor() {
        TopicDTO topicDTO = new TopicDTO();

        assertNull(topicDTO.getTopicId());
        assertNull(topicDTO.getTopicName());
    }
}
