package com.thbs.learningplan.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BatchCourseId implements Serializable {

    private Long batchId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private LearningPlan learningPlan;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Constructors, getters, and setters
    // Implement equals() and hashCode() methods
}
