package com.thbs.learningplan.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code TrainerBatchCourseDTO} class represents a data transfer object
 * (DTO) for a batch course associated with a specific trainer.
 * It encapsulates information about the trainer, course, and scheduling
 * details.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerBatchCourseDTO {

    private Long batchId;

    /**
     * The ID of the trainer.
     */
    private Long trainerId;

    /**
     * The name of the trainer.
     */
    private String trainer;

    /**
     * The ID of the course.
     */
    private Long courseId;

    /**
     * The name of the course.
     */
    private String courseName;

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
}
