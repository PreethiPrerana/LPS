package com.thbs.learningplan.testService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.SubTopicRepository;
import com.thbs.learningplan.service.SubTopicService;

@ExtendWith(MockitoExtension.class)
class SubTopicServiceTest {

    @Mock
    private SubTopicRepository subTopicRepository;

    @InjectMocks
    private SubTopicService subTopicService;

    @Test
    void testSaveSubTopic_InvalidDataException() {
        SubTopic subTopic = new SubTopic(); // SubTopic with no name
        assertThrows(InvalidDataException.class, () -> subTopicService.saveSubTopic(subTopic));
    }

    @Test
    void testSaveSubTopic_SuccessfulSaving() {
        SubTopic subTopic = new SubTopic();
        subTopic.setSubTopicName("Introduction to Java");
        when(subTopicRepository.save(any(SubTopic.class))).thenReturn(subTopic);
        assertEquals(subTopic, subTopicService.saveSubTopic(subTopic));
    }

    @Test
    void testSaveMultipleSubTopics_InvalidDataException() {
        List<SubTopic> subTopics = new ArrayList<>();
        subTopics.add(new SubTopic()); // SubTopic with no name
        assertThrows(InvalidDataException.class, () -> subTopicService.saveMultipleSubTopics(subTopics));
    }

    @Test
    void testSaveMultipleSubTopics_SuccessfulSaving() {
        List<SubTopic> subTopics = Arrays.asList(
                new SubTopic(1L, "Introduction to Java", new Topic()),
                new SubTopic(2L, "Advanced Java", new Topic()));

        when(subTopicRepository.save(any(SubTopic.class))).thenReturn(new SubTopic());

        List<SubTopic> savedSubTopics = subTopicService.saveMultipleSubTopics(subTopics);
        assertEquals(subTopics.size(), savedSubTopics.size());
    }

    @Test
    void testGetSubTopicById_SuccessfulRetrieval() {
        Long subTopicId = 1L;
        SubTopic subTopic = new SubTopic(subTopicId, "Introduction to Java", new Topic());
        when(subTopicRepository.findById(subTopicId)).thenReturn(Optional.of(subTopic));
        assertEquals(subTopic, subTopicService.getSubTopicById(subTopicId));
    }

    @Test
    void testGetSubTopicById_NotFoundException() {
        Long subTopicId = 1L;
        when(subTopicRepository.findById(subTopicId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subTopicService.getSubTopicById(subTopicId));
    }

    @Test
    void testGetSubTopicsByTopic_SuccessfulRetrieval() {
        Topic topic = new Topic(1L, "Java Basics", 1.0,null);
        List<SubTopic> subTopics = Arrays.asList(
                new SubTopic(1L, "Introduction to Java", topic),
                new SubTopic(2L, "Advanced Java", topic));

        when(subTopicRepository.findByTopic(topic)).thenReturn(subTopics);

        assertEquals(subTopics, subTopicService.getSubTopicsByTopic(topic));
    }

    @Test
    void testDeleteSubTopic_SuccessfulDeletion() {
        Long subTopicId = 1L;
        doNothing().when(subTopicRepository).deleteById(subTopicId);
        subTopicService.deleteSubTopic(subTopicId);
        verify(subTopicRepository, times(1)).deleteById(subTopicId);
    }
}
