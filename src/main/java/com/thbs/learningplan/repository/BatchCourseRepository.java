package com.thbs.learningplan.repository;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * Retrieves a list of batch courses associated with a specific trainer ID.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of batch courses associated with the specified trainer ID.
     */
    List<BatchCourse> findByTrainerId(Long trainerId);

    /**
     * Retrieves a list of distinct batch IDs from the database.
     * 
     * @return A list of Long representing distinct batch IDs.
     */
    @Query("SELECT DISTINCT b.batchCourseId.batchId FROM BatchCourse b")
    List<Long> findDistinctBatchIds();
}
