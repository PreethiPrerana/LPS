package com.thbs.learningplan.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.thbs.learningplan.dto.SubTopicDTO;
import com.thbs.learningplan.dto.TopicDTO;

class TopicDTOTest {
    @Test
    void testGetterAndSetter() {
        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setTopicId(1L);
        topicDTO.setTopicName("Java");

        List<SubTopicDTO> subTopics = new ArrayList<>();
        subTopics.add(new SubTopicDTO()); // Assuming SubTopicDTO has a no-args constructor
        topicDTO.setSubTopics(subTopics);

        assertEquals(1L, topicDTO.getTopicId());
        assertEquals("Java", topicDTO.getTopicName());
        assertEquals(subTopics, topicDTO.getSubTopics());
    }

    @Test
    void testAllArgsConstructor() {
        List<SubTopicDTO> subTopics = new ArrayList<>();
        subTopics.add(new SubTopicDTO()); // Assuming SubTopicDTO has a no-args constructor

        TopicDTO topicDTO = new TopicDTO(1L, "Java", subTopics);

        assertEquals(1L, topicDTO.getTopicId());
        assertEquals("Java", topicDTO.getTopicName());
        assertEquals(subTopics, topicDTO.getSubTopics());
    }

    @Test
    void testNoArgsConstructor() {
        TopicDTO topicDTO = new TopicDTO();

        assertNull(topicDTO.getTopicId());
        assertNull(topicDTO.getTopicName());
        assertNull(topicDTO.getSubTopics());
    }
}
