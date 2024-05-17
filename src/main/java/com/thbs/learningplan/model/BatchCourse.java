package com.thbs.learningplan.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code BatchCourse} class represents a batch course entity in the system.
 * It encapsulates information about a specific course scheduled within a batch,
 * including the start and end dates, and the assigned trainer.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batch_course")
public class BatchCourse {

    /**
     * The composite primary key representing the association between a batch, a
     * course, and a learning plan.
     */
    @EmbeddedId
    private BatchCourseId batchCourseId;

    /**
     * The start date of the batch course.
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * The end date of the batch course.
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Long trainerId;
    
    /**
     * The trainer assigned to conduct the batch course.
     */
    private String trainer;
}
