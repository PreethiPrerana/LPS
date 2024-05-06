package com.thbs.learningplan.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
