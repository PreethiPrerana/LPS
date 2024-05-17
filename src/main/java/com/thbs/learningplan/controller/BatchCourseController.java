package com.thbs.learningplan.controller;

import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.dto.TrainerBatchCourseDTO;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.utility.DateRange;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * The {@code BatchCourseController} class defines RESTful endpoints for
 * managing batch courses.
 * It provides methods for adding, retrieving, updating, and deleting batch
 * courses.
 */
@RestController
@RequestMapping("/batch-course")
public class BatchCourseController {

    private final BatchCourseService batchCourseService;

    /**
     * Constructs a new {@code BatchCourseController} with the specified batch
     * course service.
     *
     * @param batchCourseService the batch course service to be injected
     */
    @Autowired
    public BatchCourseController(BatchCourseService batchCourseService) {
        this.batchCourseService = batchCourseService;
    }

    /**
     * Endpoint for adding a single batch course.
     *
     * @param batchCourse the batch course to be added
     * @return the added batch course with HTTP status 200 OK
     */
    @PostMapping
    public ResponseEntity<BatchCourse> addBatchCourse(@RequestBody BatchCourse batchCourse) {
        BatchCourse addedBatchCourse = batchCourseService.addBatchCourse(batchCourse);
        return ResponseEntity.ok().body(addedBatchCourse);
    }

    /**
     * Endpoint for adding multiple batch courses.
     *
     * @param batchCourses the list of batch courses to be added
     * @return the list of added batch courses with HTTP status 200 OK
     */
    @PostMapping("/multiple")
    public ResponseEntity<List<BatchCourse>> addMultipleBatchCourses(@RequestBody List<BatchCourse> batchCourses) {
        List<BatchCourse> addedBatchCourses = batchCourseService.addMultipleBatchCourses(batchCourses);
        return ResponseEntity.ok().body(addedBatchCourses);
    }

    /**
     * Endpoint for retrieving all batch courses.
     *
     * @return the list of batch courses with HTTP status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<BatchCourse>> getAllBatchCourses() {
        List<BatchCourse> batchCourses = batchCourseService.getAllBatchCourses();
        return ResponseEntity.ok().body(batchCourses);
    }

    /**
     * Retrieves all batch courses associated with a specific trainer ID.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of {@code TrainerBatchCourseDTO} containing the batch courses
     *         associated with the trainer.
     */
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<TrainerBatchCourseDTO>> getBatchCoursesByTrainerId(@PathVariable Long trainerId) {
        List<TrainerBatchCourseDTO> batchCourses = batchCourseService.getBatchCoursesByTrainerId(trainerId);
        return ResponseEntity.ok().body(batchCourses);
    }

    /**
     * Endpoint for retrieving batch course DTO by batch ID.
     *
     * @param batchId the ID of the batch
     * @return the batch course DTO with HTTP status 200 OK
     */
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<CourseByBatchDTO> getBatchCourseDTO(@PathVariable Long batchId) {
        CourseByBatchDTO dto = batchCourseService.convertToDTO(batchId);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Endpoint for generating plan DTO by batch ID.
     *
     * @param batchId the ID of the batch
     * @return the plan DTO with HTTP status 200 OK
     */
    @GetMapping("/view-dto/{batchId}")
    public ResponseEntity<PlanDTO> getPlanDTO(@PathVariable Long batchId) {
        PlanDTO dto = batchCourseService.generatePlanDTO(batchId);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Endpoint for updating trainer.
     *
     * @param batchCourseId the ID of the batch course
     * @param trainer       the new trainer
     * @return a success message with HTTP status 200 OK
     */
    @PutMapping("/trainer")
    public ResponseEntity<String> updateTrainer(@RequestBody BatchCourseId batchCourseId,
            @RequestParam String trainer, @RequestParam Long trainerId) {
        batchCourseService.updateTrainer(batchCourseId, trainerId, trainer);
        return ResponseEntity.ok().body("Trainer updated successfully");

    }

    /**
     * Endpoint for updating batch course dates.
     *
     * @param dateRange the date range for the batch course
     * @return a success message with HTTP status 200 OK
     */
    @PutMapping("/dates")
    public ResponseEntity<String> updateDates(@RequestBody DateRange dateRange) {
        batchCourseService.updateDates(dateRange);

        return ResponseEntity.ok().body("Dates updated Successfully");
    }

    /**
     * Endpoint for deleting a batch course.
     *
     * @param batchCourseId the ID of the batch course
     * @return a success message with HTTP status 200 OK
     */
    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteBatchCourse(@RequestBody BatchCourseId batchCourseId) {
        batchCourseService.deleteBatchCourse(batchCourseId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
