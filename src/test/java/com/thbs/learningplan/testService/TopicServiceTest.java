package com.thbs.learningplan.testService;

import com.thbs.learningplan.exception.*;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.TopicRepository;
import com.thbs.learningplan.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = new Topic();
        topic.setTopicName("Test Topic");
        topic.setDescription("Test Description");
    }

@Test
void testAddTopicWithValidation_Success() {
    // Stub the method call with specific arguments
    when(topicRepository.existsByTopicNameAndCourse(eq(topic.getTopicName()), eq(topic.getCourse()))).thenReturn(false);

    // Stub the save method to return the topic
    when(topicRepository.save(any(Topic.class))).thenReturn(topic);

    // Call the method under test
    Topic addedTopic = topicService.addTopicWithValidation(topic);

    // Assertions
    assertNotNull(addedTopic);
    assertEquals(topic, addedTopic);
}

    @Test
    void testAddTopicWithValidation_InvalidDataException() {
        Topic topic1 = new Topic(1L, "", "Description", new Course());
        Topic topic2 = new Topic(1L, null, "Description", new Course());
        Topic topic3 = new Topic(1L, "New Topic", "", new Course());
        Topic topic4 = new Topic(1L, "New Topic", null, new Course());

        assertThrows(InvalidDataException.class, () -> topicService.addTopicWithValidation(topic1));
        assertThrows(InvalidDataException.class, () -> topicService.addTopicWithValidation(topic2));
        assertThrows(InvalidDataException.class, () -> topicService.addTopicWithValidation(topic3));
        assertThrows(InvalidDataException.class, () -> topicService.addTopicWithValidation(topic4));
    }

    @Test
    void testAddTopicWithValidation_DuplicateEntryException() {
        when(topicRepository.existsByTopicNameAndCourse(anyString(), any(Course.class))).thenReturn(true);
        Topic topic = new Topic(1L, "Existing Topic", "Description", new Course());

        assertThrows(DuplicateEntryException.class, () -> topicService.addTopicWithValidation(topic));
    }

    @Test
    void testAddTopicsWithValidation_Success() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic validTopic2 = new Topic();
        validTopic2.setTopicName("Topic 2");
        validTopic2.setDescription("Description 2");
        validTopic2.setCourse(course);
        topics.add(validTopic1);
        topics.add(validTopic2);

        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(false);

        List<Topic> addedTopics = assertDoesNotThrow(() -> {
            return topicService.addTopicsWithValidation(topics);
        });

        assertEquals(topics.size(), addedTopics.size());
    }

    @Test
    void testAddTopicsWithValidation_DuplicateEntryException() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic1 = new Topic();
        validTopic1.setTopicName("Topic 1");
        validTopic1.setDescription("Description 1");
        Course course = new Course();
        validTopic1.setCourse(course);

        Topic duplicateTopic = new Topic();
        duplicateTopic.setTopicName("Topic 1");
        duplicateTopic.setDescription("Description 1");
        duplicateTopic.setCourse(course);

        topics.add(validTopic1);
        topics.add(duplicateTopic);

        when(topicRepository.existsByTopicNameAndCourse(any(), any())).thenReturn(true);

        assertThrows(DuplicateEntryException.class, () -> {
            topicService.addTopicsWithValidation(topics);
        });
    }

    @Test
    void testAddTopicsWithValidation_InvalidDataException() {
        List<Topic> topics = new ArrayList<>();

        Topic validTopic = new Topic();
        validTopic.setTopicName("");
        validTopic.setDescription("Description 1");
        Course course = new Course();
        validTopic.setCourse(course);

        topics.add(validTopic);

        validTopic.setTopicName(null);

        topics.add(validTopic);

        validTopic.setTopicName("Topic 1");
        validTopic.setDescription("");

        topics.add(validTopic);

        validTopic.setTopicName("Topic 2");
        validTopic.setDescription(null);

        topics.add(validTopic);

        assertThrows(InvalidDataException.class,
                () -> topicService.addTopicsWithValidation(topics));
    }

    @Test
    void testGetAllTopics_Success() {
        List<Topic> expectedTopics = new ArrayList<>();

        expectedTopics.add(new Topic());

        when(topicRepository.findAll()).thenReturn(expectedTopics);

        List<Topic> actualTopics = topicService.getAllTopics();

        assertEquals(expectedTopics.size(), actualTopics.size());
    }

    @Test
    void testGetTopicById_Success() {
        // Mock data
        Long topicId = 123L;
        Topic expectedTopic = new Topic(/* Add constructor parameters here */);
        Optional<Topic> optionalTopic = Optional.of(expectedTopic);

        // Mock repository method
        when(topicRepository.findById(topicId)).thenReturn(optionalTopic);

        // Call service method
        Topic topic = topicService.getTopicById(topicId);

        // Verify that repository method was called with correct parameter
        verify(topicRepository).findById(topicId);

        // Verify returned topic
        assertEquals(expectedTopic, topic);
    }

    @Test
    void testGetTopicById_NotFoundException() {
        // Mock data
        Long topicId = 123L;
        Optional<Topic> optionalTopic = Optional.empty();

        // Mock repository method
        when(topicRepository.findById(topicId)).thenReturn(optionalTopic);

        // Call service method - should throw NotFoundException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            topicService.getTopicById(topicId);
        });

        // Optionally, assert the exception message or properties
        assertEquals("Topic not found.", exception.getMessage());
    }

    @Test
    void testGetTopicsByCourse_Success() {
        Course course = new Course();
        course.setCourseId(1L);

        Topic topic1 = new Topic();
        topic1.setCourse(course);

        Topic topic2 = new Topic();
        topic2.setCourse(course);

        List<Topic> topics = new ArrayList<>();
        topics.add(topic1);
        topics.add(topic2);

        when(topicRepository.findByCourse(course)).thenReturn(topics);

        List<Topic> result = topicService.getTopicsByCourse(course);

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_Success() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        String updatedTopic = topicService.updateTopicDescriptionWithValidation(1L, "New Description");

        assertEquals("Description updated successfully", updatedTopic);
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_InvalidDataException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidDataException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, ""));

        assertThrows(InvalidDataException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, null));
    }

    @Test
    void testUpdateTopicDescriptionWithValidation_NotFoundException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> topicService.updateTopicDescriptionWithValidation(1L, "New Description"));
    }

    @Test
    void testUpdateTopicNameWithValidation_Success() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        String updatedTopic = topicService.updateTopicNameWithValidation(1L, "New Name");

        assertEquals("Topic name updated successfully", updatedTopic);
    }

    @Test
    void testUpdateTopicNameWithValidation_InvalidDataException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));

        assertThrows(InvalidDataException.class,
                () -> topicService.updateTopicNameWithValidation(1L, ""));

        assertThrows(InvalidDataException.class,
                () -> topicService.updateTopicNameWithValidation(1L, null));
    }

    @Test
    void testUpdateTopicNameWithValidation_NotFoundException() {
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> topicService.updateTopicNameWithValidation(1L, "New Name"));
    }

    @Test
    void testDeleteTopicById_Success() {
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        assertDoesNotThrow(() -> {
            topicService.deleteTopicById(1L);
        });

        verify(topicRepository, times(1)).delete(topic);
    }

    @Test
    void testDeleteTopicById_NotFoundException() {
        when(topicRepository.findById(eq(1L))).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            topicService.deleteTopicById(1L);
        });

        assertEquals("Topic not found.", exception.getMessage());

        verify(topicRepository).findById(eq(1L));

        verify(topicRepository, never()).delete(any());
    }

    @Test
    void testDeleteTopics_Success() {
        List<Topic> topics = new ArrayList<>();

        Topic topic1 = new Topic();
        topic1.setTopicId(1L);

        Topic topic2 = new Topic();
        topic2.setTopicId(2L);

        topics.add(topic1);
        topics.add(topic2);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic1));

        when(topicRepository.findById(2L)).thenReturn(Optional.of(topic2));

        assertDoesNotThrow(() -> {
            topicService.deleteTopics(topics);
        });

        verify(topicRepository, times(1)).delete(topic1);
        verify(topicRepository, times(1)).delete(topic2);
    }

    @Test
    void testDeleteTopics_NotFoundException() {
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        List<Topic> topicsToDelete = new ArrayList<Topic>();
        Topic topic1 = new Topic();
        topic1.setTopicId(1L);
        topicsToDelete.add(topic1);

        Topic topic2 = new Topic();
        topic2.setTopicId(1L);
        topicsToDelete.add(topic2);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            topicService.deleteTopics(topicsToDelete);
        });

        assertEquals("Topic not found.", exception.getMessage());
    }

    @Test
    void testDeleteTopicsByCourse_Success() {
        List<Topic> topics = new ArrayList<>();

        topics.add(new Topic());
        topics.add(new Topic());

        when(topicRepository.findByCourse(any(Course.class))).thenReturn(topics);

        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        verify(topicRepository, times(2)).delete(any(Topic.class));
    }

    @Test
    void testDeleteTopicsByCourse_NoTopicsFound() {
        when(topicRepository.findByCourse(any(Course.class))).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> topicService.deleteTopicsByCourse(new Course()));

        verify(topicRepository, never()).delete(any(Topic.class));
    }
}
