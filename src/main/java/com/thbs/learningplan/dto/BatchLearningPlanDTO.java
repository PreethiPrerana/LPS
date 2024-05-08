package com.thbs.learningplan.dto;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The {@code BatchLearningPlanDTO} class represents a data transfer object (DTO) for a
 * learning plan in a particular batch.
 * It encapsulates information about a learning plan, including its ID, name, type,
 * the associated batch ID, and the list of courses sorted by courseId.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchLearningPlanDTO {
    /**
     * The unique identifier for the learning plan.
     */
    private Long learningPlanId;

    /**
     * The name of the learning plan.
     */
    private String learningPlanName;

    /**
     * The type of the learning plan.
     */
    private String type;

    /**
     * The unique identifier for the batch.
     */
    private Long batchId;

    /**
     * The list of courses associated with the learning plan in the batch, sorted by courseId.
     */
    private List<BatchCourse> batchCourses;
}
