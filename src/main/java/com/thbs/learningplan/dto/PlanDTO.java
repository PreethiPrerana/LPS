package com.thbs.learningplan.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code PlanDTO} class represents a data transfer object (DTO) for a
 * learning plan.
 * It encapsulates information about a learning plan, including its ID, name,
 * type, and associated batch courses.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {

    /**
     * The ID of the batch associated with the learning plan.
     */
    private Long batchId;

    /**
     * The ID of the learning plan.
     */
    private Long learningPlanId;

    /**
     * The name of the learning plan.
     */
    private String learningPlanName;

    /**
     * The type of the learning plan.
     */
    private String learningPlanType;

    /**
     * The list of batch courses associated with the learning plan.
     */
    private List<BatchCourseDTO> batchCourses;

}
