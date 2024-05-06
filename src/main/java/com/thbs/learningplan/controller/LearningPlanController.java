package com.thbs.learningplan.controller;

import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/learning-plan")
public class LearningPlanController {

    @Autowired
    private LearningPlanService learningPlanService;

    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan savedPlan = learningPlanService.saveLearningPlan(learningPlan);
        return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return new ResponseEntity<>(learningPlans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningPlan> getLearningPlanById(@PathVariable("id") Long learningPlanId) {
        Optional<LearningPlan> learningPlan = learningPlanService.getLearningPlanById(learningPlanId);
        return learningPlan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable("type") String type) {
        List<LearningPlan> learningPlans = learningPlanService.getLearningPlansByType(type);
        return new ResponseEntity<>(learningPlans, HttpStatus.OK);
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<LearningPlan> updateLearningPlanName(@PathVariable("id") Long learningPlanId,
                                                               @RequestParam("name") String newLearningPlanName) {
        LearningPlan updatedLearningPlan = learningPlanService.updateLearningPlanName(learningPlanId, newLearningPlanName);
        if (updatedLearningPlan != null) {
            return new ResponseEntity<>(updatedLearningPlan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearningPlanById(@PathVariable("id") Long learningPlanId) {
        learningPlanService.deleteLearningPlanById(learningPlanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
