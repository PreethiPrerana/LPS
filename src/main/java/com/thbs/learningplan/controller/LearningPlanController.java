package com.thbs.learningplan.controller;

import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning-plan")
@CrossOrigin("172.18.4.113:5173, 172.18.4.195:5173,172.18.4.190:5173 ")
public class LearningPlanController {

    private LearningPlanService learningPlanService;

    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService) {
        this.learningPlanService = learningPlanService;
    }

    @PostMapping
    public ResponseEntity<LearningPlan> saveLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan savedPlan = learningPlanService.saveLearningPlan(learningPlan);
        return ResponseEntity.ok().body(savedPlan);
    }

    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return ResponseEntity.ok().body(learningPlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningPlan> getLearningPlanById(@PathVariable("id") Long learningPlanId) {
        LearningPlan learningPlan = learningPlanService.getLearningPlanById(learningPlanId);
        return ResponseEntity.ok().body(learningPlan);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<LearningPlan>> getLearningPlansByType(@PathVariable("type") String type) {
        List<LearningPlan> learningPlans = learningPlanService.getLearningPlansByType(type);
        return ResponseEntity.ok().body(learningPlans);
    }

    @PutMapping("/name/{id}")
    public ResponseEntity<LearningPlan> updateLearningPlanName(@PathVariable("id") Long learningPlanId,
            @RequestParam("name") String newLearningPlanName) {
        LearningPlan updatedLearningPlan = learningPlanService.updateLearningPlanName(learningPlanId,
                newLearningPlanName);
        return ResponseEntity.ok().body(updatedLearningPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLearningPlanById(@PathVariable("id") Long learningPlanId) {
        learningPlanService.deleteLearningPlanById(learningPlanId);
        return ResponseEntity.ok().body("Learning Plan deleted successfully");
    }
}
