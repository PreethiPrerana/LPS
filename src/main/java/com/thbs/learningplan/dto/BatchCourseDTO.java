package com.thbs.learningplan.dto;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code BatchCourseDTO} class represents a data transfer object (DTO) for
 * batch courses.
 * It encapsulates information about a batch course, including its start date,
 * end date, trainer,
 * associated course name, course ID, and list of topics.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchCourseDTO {
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

    /**
     * The trainer assigned to the batch course.
     */
    private String trainer;

    /**
     * The name of the associated course.
     */
    private String courseName;

    /**
     * The ID of the associated course.
     */
    private Long courseId;

    /**
     * The list of topics associated with the batch course.
     */
    private List<TopicDTO> topic;
}