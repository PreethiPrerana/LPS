// package com.thbs.learningplan.model;

// import java.util.Date;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// @Entity
// @Table(name = "batch_course")
// public class BatchCourse {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long batchCourseId;

//     private Long batchId;

//     @ManyToOne
//     @JoinColumn(name = "plan_id")
//     private LearningPlan learningPlan;

//     @ManyToOne
//     @JoinColumn(name = "course_id")
//     private Course course;

//     @Temporal(TemporalType.DATE)
//     private Date startDate;

//     @Temporal(TemporalType.DATE)
//     private Date endDate;

//     private String trainer;
// }

package com.thbs.learningplan.model;

import java.util.Date;
import jakarta.persistence.*;
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
@Entity
@Table(name = "batch_course")
public class BatchCourse {

    @EmbeddedId
    private BatchCourseId batchCourseId;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String trainer;
}
