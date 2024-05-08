package com.thbs.learningplan.repository;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The {@code BatchCourseRepository} interface is responsible for providing CRUD
 * operations for BatchCourse entities.
 * It extends the JpaRepository interface to inherit basic CRUD functionalities.
 */
@Repository
public interface BatchCourseRepository extends JpaRepository<BatchCourse, BatchCourseId> {

    /**
     * Find a list of BatchCourse entities by BatchCourseId.
     * 
     * @param batchCourseId The BatchCourseId to search for.
     * @return A list of BatchCourse entities.
     */
    List<BatchCourse> findByBatchCourseId(BatchCourseId batchCourseId);

    /**
     * Delete BatchCourse entities by BatchCourseId.
     * 
     * @param batchId The BatchCourseId to delete.
     */
    void deleteByBatchCourseId(BatchCourseId batchId);

    /**
     * Find a list of BatchCourse entities by batchId.
     * 
     * @param batchId The ID of the batch.
     * @return A list of BatchCourse entities.
     */
    List<BatchCourse> findByBatchCourseIdBatchId(Long batchId);

}
