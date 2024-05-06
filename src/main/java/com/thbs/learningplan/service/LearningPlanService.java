package com.thbs.learningplan.service;

import com.thbs.learningplan.exception.DuplicateEntryException;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.repository.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningPlanService {

    private static final String NOT_FOUND_MSG = "Learning plan not found.";
    private LearningPlanRepository learningPlanRepository;

    @Autowired
    public LearningPlanService(LearningPlanRepository learningPlanRepository) {
        this.learningPlanRepository = learningPlanRepository;
    }

    /**
     * Saves a new learning plan to the database.
     *
     * @param learningPlan The learning plan to be saved.
     * @return The saved learning plan.
     * @throws DuplicateEntryException If a learning plan with same name already
     *                                 exists
     * @throws InvalidDataException    If the learning plan name or learning plan
     *                                 type is null.
     */
    public LearningPlan saveLearningPlan(LearningPlan learningPlan) {
        // Throws exception if duplicate learning plan or invalid data

        if (learningPlan.getType() == null || learningPlan.getType().isEmpty()
                || learningPlan.getLearningPlanName() == null || learningPlan.getLearningPlanName().isEmpty()) {
            throw new InvalidDataException("Learning Plan Type or Learning Plan Name cannot be null or empty");
        }

        List<LearningPlan> existingLearningPlan = learningPlanRepository
                .findByLearningPlanNameIgnoreCase(learningPlan.getLearningPlanName());
        if (!existingLearningPlan.isEmpty()) {
            throw new DuplicateEntryException("Learning plan with this name already exists.");
        }

        return learningPlanRepository.save(learningPlan);
    }

    /**
     * Retrieves all learning plans from the database.
     *
     * @return The list of all learning plans.
     */
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAll();
    }

    /**
     * Retrieves a learning plan by its ID from the database.
     *
     * @param learningPlanId The ID of the learning plan.
     * @return The learning plan with the specified ID.
     * @throws NotFoundException If the learning plan with the specified
     *                           ID is not found.
     */
    public LearningPlan getLearningPlanById(Long learningPlanId) {
        return learningPlanRepository.findById(learningPlanId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
    }

    /**
     * Retrieves learning plans by type from the database.
     *
     * @param type The type of the learning plans.
     * @return The list of learning plans with the specified type.
     * @throws InvalidDataException If the type is null.
     * @throws NotFoundException    If no learning plans are found with the
     *                              specified type.
     */
    public List<LearningPlan> getLearningPlansByType(String type) {
        // Validates type and retrieves learning plans by type
        if (type == null || type.isEmpty()) {
            throw new InvalidDataException("Learning Plan Type cannot be null");
        }
        List<LearningPlan> learningPlan = learningPlanRepository.findByType(type);
        if (!learningPlan.isEmpty()) {
            return learningPlan;
        } else {
            // Throws exception if no learning plans found
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }

    /**
     * Updates the name of a learning plan.
     *
     * @param learningPlanId      The ID of the learning plan to update.
     * @param newLearningPlanName The new name for the learning plan.
     * @return The updated learning plan.
     * @throws NotFoundException If the learning plan with the specified
     *                           ID is not found.
     */
    public LearningPlan updateLearningPlanName(Long learningPlanId, String newLearningPlanName) {
        LearningPlan learningPlan = learningPlanRepository.findById(learningPlanId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG));
        learningPlan.setLearningPlanName(newLearningPlanName);
        return learningPlanRepository.save(learningPlan);
    }

    /**
     * Deletes a learning plan by its ID from the database along with its associated
     * paths.
     *
     * @param learningPlanId The ID of the learning plan to delete.
     * @throws NotFoundException If the learning plan with the specified
     *                           ID is not found.
     */
    public void deleteLearningPlanById(Long learningPlanId) {
        if (!learningPlanRepository.existsById(learningPlanId)) {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
        learningPlanRepository.deleteById(learningPlanId);
    }
}
