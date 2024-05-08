package com.thbs.learningplan.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code BatchCourseId} class represents the composite primary key for the
 * {@code BatchCourse} entity.
 * It encapsulates the identifiers of the batch, learning plan, and course
 * associated with a batch course.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BatchCourseId implements Serializable {

    /**
     * The identifier of the batch.
     */
    private Long batchId;

    /**
     * The learning plan associated with the batch course.
     */
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private LearningPlan learningPlan;
    
    /**
     * The course associated with the batch course.
     */
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
