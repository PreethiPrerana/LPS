package com.thbs.learningplan.service;

import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.repository.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    // Method to save a learning plan
    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        return learningPlanRepository.save(learningPlan);
    }

    // Method to read all learning plans
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    // Method to read a learning plan by ID
    public Optional<LearningPlan> getLearningPlanById(Long learningPlanId) {
        return learningPlanRepository.findById(learningPlanId);
    }

    // Method to read learning plans by type
    public List<LearningPlan> getLearningPlansByType(String type) {
        return learningPlanRepository.findByType(type);
    }

    // Method to update learning plan name
    public LearningPlan updateLearningPlanName(Long learningPlanId, String newLearningPlanName) {
        Optional<LearningPlan> optionalLearningPlan = learningPlanRepository.findById(learningPlanId);
        if (optionalLearningPlan.isPresent()) {
            LearningPlan learningPlan = optionalLearningPlan.get();
            learningPlan.setLearningPlanName(newLearningPlanName);
            return learningPlanRepository.save(learningPlan);
        }
        return null; // Or throw exception indicating learning plan not found
    }

    // Method to delete a learning plan by ID
    public void deleteLearningPlanById(Long learningPlanId) {
        learningPlanRepository.deleteById(learningPlanId);
    }
}
