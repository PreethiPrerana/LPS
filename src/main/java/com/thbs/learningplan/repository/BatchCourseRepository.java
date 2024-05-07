package com.thbs.learningplan.repository;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchCourseRepository extends JpaRepository<BatchCourse, BatchCourseId> {

    // Find BatchCourse by BatchCourseId
    List<BatchCourse> findByBatchCourseId(BatchCourseId batchCourseId);

    void deleteByBatchCourseId(BatchCourseId batchId);

    List<BatchCourse> findByBatchCourseIdBatchId(Long batchId);

}
