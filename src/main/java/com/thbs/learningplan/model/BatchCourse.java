package com.thbs.learningplan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BatchCourse")
public class BatchCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Embedded
    private LearningPlanCourseId learningPlanCourseId;

    /**
     * The start date of the module.
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * The end date of the module.
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private String trainer;
}