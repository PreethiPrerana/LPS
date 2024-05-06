package com.thbs.learningplan.model;

import java.util.List;

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
public class LearningPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learningPlanId;

    private String learningPlanName;
    private String type;

    @ElementCollection
    @CollectionTable(name = "LearningPlan_Course", joinColumns = @JoinColumn(name = "learning_plan_id", insertable = false, updatable = false))
    @AttributeOverride(name = "courseId", column = @Column(name = "course_id", insertable = false, updatable = false))
    private List<LearningPlanCourseId> learningPlanCourses;
}