package com.thbs.learningplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thbs.learningplan.model.LearningPlan;

import java.util.List;
import java.util.Optional;

/**
 * The {@code LearningPlanRepository} interface provides CRUD operations for the
 * {@link com.thbs.lms.model.LearningPlan} entity.
 */
@Repository
public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {

    /**
     * Retrieves a list of learning plans by their type.
     *
     * @param type The type of the learning plans to retrieve.
     * @return A list of learning plans with the specified type.
     */
    List<LearningPlan> findByType(String type);

    /**
     * Retrieves a list of learning plans by their name, ignoring case.
     *
     * @param learningPlanName The name of the learning plans to retrieve.
     * @return A list of learning plans with the specified name.
     */
    List<LearningPlan> findByLearningPlanNameIgnoreCase(String learningPlanName);

    /**
     * Retrieves a learning plan by its ID.
     *
     * @param learningPlanId The ID of the learning plan to retrieve.
     * @return An optional containing the learning plan with the specified ID, or
     *         empty if not found.
     */
    Optional<LearningPlan> findByLearningPlanId(Long learningPlanId);

}
