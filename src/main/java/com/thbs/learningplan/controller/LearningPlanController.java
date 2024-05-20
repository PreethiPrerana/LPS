package com.thbs.learningplan.controller;

import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.service.LearningPlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code LearningPlanController} class defines RESTful endpoints for
 * managing learning plans.
 * It provides methods for adding, retrieving, updating, and deleting learning
 * plans.
 */
@RestController
@RequestMapping("/learning-plan")
@Tag(name = "Learning Plan", description = "Operations related to learning plans")
public class LearningPlanController {

    private LearningPlanService learningPlanService;

    /**
     * Constructs a new {@code LearningPlanController} with the specified learning
     * plan service.
     *
     * @param learningPlanService the learning plan service to be injected
     */
    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService) {
        this.learningPlanService = learningPlanService;
    }

    /**
     * Endpoint for saving a new learning plan.
     *
     * @param learningPlan the learning plan to be saved
     * @return the saved learning plan with HTTP status 200 OK
     */
    @Operation(summary = "Save a new learning plan")
    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan savedPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(savedPlan);
    }

    /**
     * Endpoint for retrieving all learning plans.
     *
     * @return the list of learning plans with HTTP status 200 OK
     */
    @Operation(summary = "Retrieve all learning plans")
    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    /**
     * Endpoint for retrieving a learning plan by its ID.
     *
     * @param learningPlanId the ID of the learning plan to retrieve
     * @return the retrieved learning plan with HTTP status 200 OK
     */
    @Operation(summary = "Retrieve a learning plan by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<LearningPlan> getLearningPlanById(@PathVariable("id") Long learningPlanId) {
        LearningPlan learningPlan = learningPlanService.getLearningPlanById(learningPlanId);
        return ResponseEntity.ok().body(learningPlan);
    }

    /**
     * Endpoint for retrieving learning plans by their type.
     *
     * @param type the type of learning plans to retrieve
     * @return the list of learning plans with HTTP status 200 OK
     */
    @Operation(summary = "Retrieve learning plans by their type")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable("type") String type) {
        List<LearningPlan> learningPlans = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlans);
    }

    /**
     * Endpoint for updating the name of a learning plan.
     *
     * @param learningPlanId      the ID of the learning plan to update
     * @param newLearningPlanName the new name for the learning plan
     * @return the updated learning plan with HTTP status 200 OK
     */
    @Operation(summary = "Update the name of a learning plan")
    @PutMapping("/name/{id}")
    public ResponseEntity<LearningPlan> updateLearningPlanName(@PathVariable("id") Long learningPlanId,
            @RequestParam("name") String newLearningPlanName) {
        LearningPlan updatedLearningPlan = learningPlanService.updateLearningPlanName(learningPlanId,
                newLearningPlanName);
        return ResponseEntity.ok().body(updatedLearningPlan);
    }

    /**
     * Endpoint for deleting a learning plan by its ID.
     *
     * @param learningPlanId the ID of the learning plan to delete
     * @return a success message with HTTP status 200 OK
     */
    @Operation(summary = "Delete a learning plan by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLearningPlanById(@PathVariable("id") Long learningPlanId) {
        learningPlanService.deleteLearningPlanById(learningPlanId);
        return ResponseEntity.ok().body("Learning Plan deleted successfully");
    }
}
