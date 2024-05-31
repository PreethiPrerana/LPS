package com.thbs.learningplan.testController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thbs.learningplan.controller.SubTopicController;
import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.service.SubTopicService;
import com.thbs.learningplan.service.TopicService;

@ExtendWith(MockitoExtension.class)
class SubTopicControllerTest {

    @Mock
    private SubTopicService subTopicService;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private SubTopicController subTopicController;

    @Test
    void testSaveSubTopic() {
        SubTopic subTopic = new SubTopic(1L, "Introduction to Java", new Topic());
        when(subTopicService.saveSubTopic(subTopic)).thenReturn(subTopic);

        ResponseEntity<SubTopic> response = subTopicController.saveSubTopic(subTopic);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subTopic, response.getBody());
    }

    @Test
    void testSaveMultipleSubTopics() {
        List<SubTopic> subTopics = new ArrayList<>();
        subTopics.add(new SubTopic(1L, "SubTopic 1", new Topic()));
        subTopics.add(new SubTopic(2L, "SubTopic 2", new Topic()));

        when(subTopicService.saveMultipleSubTopics(subTopics)).thenReturn(subTopics);

        ResponseEntity<List<SubTopic>> response = subTopicController.saveMultipleSubTopics(subTopics);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subTopics, response.getBody());
    }

    @Test
    void testGetAllSubTopics() {
        List<SubTopic> subTopics = new ArrayList<>();
        subTopics.add(new SubTopic(1L, "SubTopic 1", new Topic()));
        subTopics.add(new SubTopic(2L, "SubTopic 2", new Topic()));

        when(subTopicService.getAllSubTopics()).thenReturn(subTopics);

        ResponseEntity<List<SubTopic>> response = subTopicController.getAllSubTopics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subTopics, response.getBody());
    }

    @Test
    void testGetSubTopicById() {
        SubTopic subTopic = new SubTopic(1L, "Introduction to Java", new Topic());
        when(subTopicService.getSubTopicById(1L)).thenReturn(subTopic);

        ResponseEntity<SubTopic> response = subTopicController.getSubTopicById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subTopic, response.getBody());
    }

    @Test
    void testGetSubTopicsByTopic() {
        Topic topic = new Topic();
        List<SubTopic> subTopics = new ArrayList<>();
        subTopics.add(new SubTopic(1L, "SubTopic 1", topic));
        subTopics.add(new SubTopic(2L, "SubTopic 2", topic));

        when(topicService.getTopicById(1L)).thenReturn(topic);
        when(subTopicService.getSubTopicsByTopic(topic)).thenReturn(subTopics);

        ResponseEntity<List<SubTopic>> response = subTopicController.getSubTopicsByTopic(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subTopics, response.getBody());
    }

    @Test
    void testDeleteSubTopic() {
        ResponseEntity<String> response = subTopicController.deleteSubTopic(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SubTopic deleted successfully", response.getBody());

        verify(subTopicService, times(1)).deleteSubTopic(1L);
    }
}
