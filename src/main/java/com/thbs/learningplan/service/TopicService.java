package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.exception.*;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code TopicService} class provides methods for managing topics.
 */
@Service
public class TopicService {

    private static final String NOT_FOUND_MSG = "Topic not found.";
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Adds a topic to the database with validation.
     *
     * @param topicName   The name of the topic.
     * @param description The description of the topic.
     * @param course      The course associated with the topic.
     * @return The added topic.
     * @throws DuplicateEntryException If a topic with the same name already exists
     *                                 for the given course.
     * @throws InvalidDataException    If the topic name, description, or course is
     *                                 null or empty.
     */
    public Topic addTopicWithValidation(Topic topic) {
        String topicName = topic.getTopicName();
        String description = topic.getDescription();
        Course course = topic.getCourse();

        // Checks for duplicate topic and validates data
        if (topicRepository.existsByTopicNameAndCourse(topicName, course)) {
            // Throws exceptions if topic already exists or data is invalid
            throw new DuplicateEntryException("Topic '" + topicName + "' already exists for this course.");
        }

        if (topicName == null || topicName.isEmpty() || description == null || description.isEmpty()) {
            throw new InvalidDataException("Topic name and description cannot be null or empty.");
        }

        Topic newTopic = new Topic();
        newTopic.setTopicName(topicName);
        newTopic.setDescription(description);
        newTopic.setCourse(course);
        return topicRepository.save(newTopic);
    }

    /**
     * Adds multiple topics to the database with validation.
     *
     * @param topics The list of topics to be added.
     * @return The list of added topics.
     */
    public List<Topic> addTopicsWithValidation(List<Topic> topics) {
        List<Topic> addedTopics = new ArrayList<>();
        for (Topic topic : topics) {
            addedTopics.add(addTopicWithValidation(topic));
        }
        return addedTopics;
    }

    /**
     * Retrieves all topics from the database.
     *
     * @return The list of all topics.
     */
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Retrieves a topic by its ID from the database.
     *
     * @param topicId The ID of the topic.
     * @return The topic with the specified ID.
     * @throws NotFoundException If the topic with the specified ID is not found.
     */
    public Topic getTopicById(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            return optionalTopic.get();
        } else {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    /**
     * Retrieves topics associated with a course from the database.
     *
     * @param course The course associated with the topics.
     * @return The list of topics associated with the specified course.
     */
    public List<Topic> getTopicsByCourse(Course course) {
        return topicRepository.findByCourse(course);
    }

    /**
     * Updates the description of a topic in the database with validation.
     *
     * @param topicId        The ID of the topic to be updated.
     * @param newDescription The new description for the topic.
     * @return A message indicating the success of the operation.
     * @throws InvalidDataException If the new description is null or empty.
     * @throws NotFoundException    If the topic with the specified ID is not
     *                              found.
     */
    public String updateTopicDescriptionWithValidation(Long topicId, String newDescription) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            // Updates topic description if exists and validates the new description
            if (newDescription == null || newDescription.isEmpty()) {
                // Throws exceptions if topic not found or description is invalid
                throw new InvalidDataException("Description cannot be null or empty.");
            }

            topic.setDescription(newDescription);
            topicRepository.save(topic);
            return "Description updated successfully";
        } else {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    /**
     * Deletes a topic from the database by its ID.
     *
     * @param topicId The ID of the topic to be deleted.
     * @throws NotFoundException If the topic with the specified ID is not
     *                           found.
     */
    public void deleteTopicById(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent()) {
            topicRepository.delete(optionalTopic.get());
        } else {
            // Throws exception if topic not found
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    /**
     * Deletes multiple topics from the database by their IDs.
     *
     * @param topics The list of topics to be deleted.
     * @throws NotFoundException If any topic in the list is not found.
     */
    public void deleteTopics(List<Topic> topics) {
        for (Topic topic : topics) {
            Long topicId = topic.getTopicId();
            Optional<Topic> optionalTopic = topicRepository.findById(topicId);
            if (optionalTopic.isPresent()) {
                topicRepository.delete(topic);
            } else {
                // Throws exception if topic not found
                throw new NotFoundException(NOT_FOUND_MSG + topicId);
            }
        }
    }

    /**
     * Deletes all topics associated with a course from the database.
     *
     * @param course The course whose associated topics are to be deleted.
     */
    public void deleteTopicsByCourse(Course course) {
        List<Topic> topics = topicRepository.findByCourse(course);
        for (Topic topic : topics) {
            topicRepository.delete(topic);
        }
    }

    /**
     * Updates the name of a topic in the database with validation.
     *
     * @param topicId The ID of the topic to be updated.
     * @param newName The new name for the topic.
     * @return A message indicating the success of the operation.
     * @throws InvalidDataException If the new name is null or empty.
     * @throws NotFoundException    If the topic with the specified ID is not
     *                              found.
     */
    public String updateTopicNameWithValidation(Long topicId, String newName) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        // Updates topic name if exists and validates the new name
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            if (newName == null || newName.isEmpty()) {
                // Throws exceptions if topic not found or name is invalid
                throw new InvalidDataException("Topic Name cannot be null or empty.");
            }

            topic.setTopicName(newName);
            topicRepository.save(topic);
            return "Topic name updated successfully";
        } else {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }
}
