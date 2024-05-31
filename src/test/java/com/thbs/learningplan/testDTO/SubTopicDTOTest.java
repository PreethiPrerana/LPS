package com.thbs.learningplan.testDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.thbs.learningplan.dto.SubTopicDTO;


class SubTopicDTOTest {

    @Test
    void testAllArgsConstructor() {
        SubTopicDTO subTopicDTO = new SubTopicDTO(1L, "Introduction to Java");

        assertEquals(1L, subTopicDTO.getSubTopidId());
        assertEquals("Introduction to Java", subTopicDTO.getSubTopicName());
    }

    @Test
    void testNoArgsConstructor() {
        SubTopicDTO subTopicDTO = new SubTopicDTO();

        assertEquals(null, subTopicDTO.getSubTopidId());
        assertEquals(null, subTopicDTO.getSubTopicName());
    }

    @Test
    void testGetterAndSetter() {
        SubTopicDTO subTopicDTO = new SubTopicDTO();

        subTopicDTO.setSubTopidId(1L);
        subTopicDTO.setSubTopicName("Introduction to Java");

        assertEquals(1L, subTopicDTO.getSubTopidId());
        assertEquals("Introduction to Java", subTopicDTO.getSubTopicName());
    }
}
