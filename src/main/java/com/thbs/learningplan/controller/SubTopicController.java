package com.thbs.learningplan.controller;

import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.service.SubTopicService;
import com.thbs.learningplan.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code SubTopicController} class provides RESTful endpoints for managing
 * sub-topics.
 */
@RestController
@RequestMapping("/sub-topic")
public class SubTopicController {

    private SubTopicService subTopicService;
    private TopicService topicService;

    @Autowired
    public SubTopicController(SubTopicService subTopicService, TopicService topicService) {
        this.subTopicService = subTopicService;
        this.topicService = topicService;
    }

    /**
     * Save a new subtopic.
     *
     * @param subTopic The subtopic to save.
     * @return The saved subtopic.
     */
    @PostMapping
    public ResponseEntity<SubTopic> saveSubTopic(@RequestBody SubTopic subTopic) {
        return ResponseEntity.ok().body(subTopicService.saveSubTopic(subTopic));
    }

    /**
     * Save multiple subtopics.
     *
     * @param subTopics The list of subtopics to save.
     * @return The list of saved subtopics.
     */
    @PostMapping("/multiple")
    public ResponseEntity<List<SubTopic>> saveMultipleSubTopics(@RequestBody List<SubTopic> subTopics) {
        return ResponseEntity.ok().body(subTopicService.saveMultipleSubTopics(subTopics));
    }

    /**
     * Get all subtopics.
     *
     * @return A list of all subtopics.
     */
    @GetMapping
    public ResponseEntity<List<SubTopic>> getAllSubTopics() {
        return ResponseEntity.ok().body(subTopicService.getAllSubTopics());
    }

    /**
     * Get a subtopic by its ID.
     *
     * @param subTopicId The ID of the subtopic to retrieve.
     * @return The subtopic if found, otherwise a 404 response.
     */
    @GetMapping("/{subTopicId}")
    public ResponseEntity<SubTopic> getSubTopicById(@PathVariable Long subTopicId) {
        return ResponseEntity.ok().body(subTopicService.getSubTopicById(subTopicId));
    }

    /**
     * Get all subtopics by topic.
     *
     * @param topic The topic to filter subtopics by.
     * @return A list of subtopics associated with the given topic.
     */
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<SubTopic>> getSubTopicsByTopic(@PathVariable Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        List<SubTopic> subTopics = subTopicService.getSubTopicsByTopic(topic);
        return ResponseEntity.ok().body(subTopics);
    }

    /**
     * Delete a subtopic by its ID.
     *
     * @param subTopicId The ID of the subtopic to delete.
     * @return A response indicating the outcome of the delete operation.
     */
    @DeleteMapping("/{subTopicId}")
    public ResponseEntity<String> deleteSubTopic(@PathVariable Long subTopicId) {
        subTopicService.deleteSubTopic(subTopicId);
        return ResponseEntity.ok().body("SubTopic deleted successfully");
    }
}
