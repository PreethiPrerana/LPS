package com.thbs.learningplan.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code CourseByBatchDTO} class represents a data transfer object (DTO)
 * for courses associated with a batch.
 * It encapsulates information about a batch, including its ID, and a list of
 * courses with their corresponding details.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseByBatchDTO {

    /**
     * The ID of the batch.
     */
    private Long batchId;

    /**
     * The list of courses associated with the batch.
     */
    private List<CourseDTO> courses;

}