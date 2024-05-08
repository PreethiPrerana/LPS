package com.thbs.learningplan.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code LearningPlan} class represents a learning plan entity in the system.
 * It encapsulates information about a learning plan, including its unique identifier,
 * name, type, and the list of courses associated with it.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "learning_plan")
public class LearningPlan {

/**
     * The unique identifier for the learning plan.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learningPlanId;

    /**
     * The name of the learning plan.
     */
    private String learningPlanName;

    /**
     * The type of the learning plan (BOOTCAMP, ORG-WIDE, MANDATORY, ON-DEMAND).
     */
    private String type;

    /**
     * The list of courses associated with the learning plan.
     */
    @ManyToMany
    @JoinTable(name = "plan_course", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();
}
