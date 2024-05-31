package com.thbs.learningplan.testModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;

class SubTopicTest {
    @Test
    void testGetterAndSetter() {
        SubTopic subTopic = new SubTopic();

        subTopic.setSubTopicId(1L);
        subTopic.setSubTopicName("Introduction to Java");

        Topic topic = new Topic();
        topic.setTopicId(1L);
        topic.setTopicName("Java Basics");

        subTopic.setTopic(topic);

        assertEquals(1L, subTopic.getSubTopicId());
        assertEquals("Introduction to Java", subTopic.getSubTopicName());
        assertEquals(topic, subTopic.getTopic());
    }

    @Test
    void testAllArgsConstructor() {
        Topic topic = new Topic();
        topic.setTopicId(1L);
        topic.setTopicName("Java Basics");

        SubTopic subTopic = new SubTopic(1L, "Introduction to Java", topic);

        assertEquals(1L, subTopic.getSubTopicId());
        assertEquals("Introduction to Java", subTopic.getSubTopicName());
        assertEquals(topic, subTopic.getTopic());
    }

    @Test
    void testNoArgsConstructor() {
        SubTopic subTopic = new SubTopic();

        assertNull(subTopic.getSubTopicId());
        assertNull(subTopic.getSubTopicName());
        assertNull(subTopic.getTopic());
    }
}
