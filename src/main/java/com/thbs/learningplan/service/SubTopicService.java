package com.thbs.learningplan.service;

import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.SubTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code SubTopicService} class provides methods for managing sub-topics.
 */
@Service
public class SubTopicService {

    private SubTopicRepository subTopicRepository;

    @Autowired
    public SubTopicService(SubTopicRepository subTopicRepository) {
        this.subTopicRepository = subTopicRepository;
    }

    /**
     * Save a new subtopic.
     *
     * @param subTopic The subtopic to save.
     * @return The saved subtopic.
     */
    public SubTopic saveSubTopic(SubTopic subTopic) {
        if (subTopic.getSubTopicName() == null || subTopic.getSubTopicName().isEmpty()) {
            throw new InvalidDataException("Sub-Topic name cannot be null or empty.");
        }
        return subTopicRepository.save(subTopic);
    }

    /**
     * Saves multiple subtopics to the database.
     *
     * This method takes a list of subtopics, iterates over each subtopic,
     * and saves each one individually using the saveSubTopic method.
     * The saved subtopics are collected into a new list, which is then returned.
     *
     * @param subTopics The list of subtopics to be saved.
     * @return A list of saved subtopics.
     */
    public List<SubTopic> saveMultipleSubTopics(List<SubTopic> subTopics) {
        List<SubTopic> newSubTopics = new ArrayList<>();
        for (SubTopic subTopic : subTopics) {
            newSubTopics.add(saveSubTopic(subTopic));
        }
        return newSubTopics;
    }

    /**
     * Get all subtopics.
     *
     * @return A list of all subtopics.
     */
    public List<SubTopic> getAllSubTopics() {
        return subTopicRepository.findAll();
    }

    /**
     * Get a subtopic by its ID.
     *
     * @param subTopicId The ID of the subtopic.
     * @return An optional containing the found subtopic, or empty if not found.
     */
    public SubTopic getSubTopicById(Long subTopicId) {
        Optional<SubTopic> subTopic = subTopicRepository.findById(subTopicId);
        if (!subTopic.isPresent()) {
            throw new NotFoundException("SubTopic for the given id not found.");
        }
        return subTopic.get();
    }

    /**
     * Get all subtopics by topic ID.
     *
     * @param topicId The ID of the topic.
     * @return A list of subtopics associated with the given topic ID.
     */
    public List<SubTopic> getSubTopicsByTopic(Topic topic) {
        return subTopicRepository.findByTopic(topic);
    }

    /**
     * Delete a subtopic by its ID.
     *
     * @param subTopicId The ID of the subtopic to delete.
     */
    public void deleteSubTopic(Long subTopicId) {
        subTopicRepository.deleteById(subTopicId);
    }
}
