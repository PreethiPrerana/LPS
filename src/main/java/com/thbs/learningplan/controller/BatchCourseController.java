package com.thbs.learningplan.controller;

import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.utility.DateRange;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/batch-course")
public class BatchCourseController {

    @Autowired
    private BatchCourseService batchCourseService;

    @PostMapping
    public ResponseEntity<BatchCourse> addBatchCourse(@RequestBody BatchCourse batchCourse) {
        BatchCourse addedBatchCourse = batchCourseService.addBatchCourse(batchCourse);
        return ResponseEntity.ok().body(addedBatchCourse);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<BatchCourse>> addMultipleBatchCourses(@RequestBody List<BatchCourse> batchCourses) {
        List<BatchCourse> addedBatchCourses = batchCourseService.addMultipleBatchCourses(batchCourses);
        return ResponseEntity.ok().body(addedBatchCourses);
    }

    @GetMapping
    public ResponseEntity<List<BatchCourse>> getAllBatchCourses() {
        List<BatchCourse> batchCourses = batchCourseService.getAllBatchCourses();
        return ResponseEntity.ok().body(batchCourses);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<CourseByBatchDTO> getBatchCourseDTO(@PathVariable Long batchId) {
        CourseByBatchDTO dto = batchCourseService.convertToDTO(batchId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/view-dto/{batchId}")
    public ResponseEntity<PlanDTO> getPlanDTO(@PathVariable Long batchId) {
        PlanDTO dto = batchCourseService.generatePlanDTO(batchId);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/trainer")
    public ResponseEntity<String> updateTrainer(@RequestBody BatchCourseId batchCourseId,
            @RequestParam String trainer) {
        batchCourseService.updateTrainer(batchCourseId, trainer);
        return ResponseEntity.ok().body("Trainer updated succesfully");

    }

    @PutMapping("/dates")
    public ResponseEntity<String> updateDates(@RequestBody DateRange dateRange) {
        batchCourseService.updateDates(dateRange);

        return ResponseEntity.ok().body("Dates updated Successfully");
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteBatchCourse(@RequestBody BatchCourseId batchCourseId) {
        batchCourseService.deleteBatchCourse(batchCourseId);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
