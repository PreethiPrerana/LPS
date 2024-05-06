package com.thbs.learningplan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// import java.util.Date;

// @Entity
// @Table(name = "BatchCourse")
// public class BatchCourse {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long batchId;

//     @Embedded
//     private LearningPlanCourseId learningPlanCourseId;

//     /**
//      * The start date of the module.
//      */
//     @Temporal(TemporalType.DATE)
//     private Date startDate;

//     /**
//      * The end date of the module.
//      */
//     @Temporal(TemporalType.DATE)
//     private Date endDate;
//     private String trainer;
// }

// import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batch_course")
public class BatchCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchCourseId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private LearningPlan learningPlan;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String trainer;

    // Constructors, getters, and setters
}
