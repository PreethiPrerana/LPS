package com.thbs.learningplan.model;

// import java.util.List;
// import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
// public class LearningPlan {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long learningPlanId;

//     private String learningPlanName;
//     private String type;

//     @ElementCollection
//     @CollectionTable(name = "LearningPlan_Course", joinColumns = @JoinColumn(name = "learning_plan_id"))
//     private List<LearningPlanCourseId> learningPlanCourses;
// }

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "learning_plan")
public class LearningPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learningPlanId;

    private String learningPlanName;

    private String type;

    @ManyToMany
    @JoinTable(name = "plan_course", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();

    // Constructors, getters, and setters
}
