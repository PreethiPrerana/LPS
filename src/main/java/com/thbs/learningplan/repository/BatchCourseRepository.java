package com.thbs.learningplan.repository;

import com.thbs.learningplan.model.BatchCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchCourseRepository extends JpaRepository<BatchCourse, Long> {

    Optional<BatchCourse> findByBatchCourseId(Long batchCourseId);

    Optional<BatchCourse> findByLearningPlan_LearningPlanId(Long learningPlanId);

    Optional<BatchCourse> findByCourse_CourseId(Long courseId);

    void deleteByBatchCourseId(Long batchCourseId);

    void deleteByLearningPlan_LearningPlanId(Long learningPlanId);

    void deleteByCourse_CourseId(Long courseId);
}
