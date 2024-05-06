package com.thbs.learningplan.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class LearningPlanCourseId implements Serializable {
    @Column(name = "learning_plan_id")
    private Long learningPlanId;

    @Column(name = "course_id")
    private Long courseId;
}