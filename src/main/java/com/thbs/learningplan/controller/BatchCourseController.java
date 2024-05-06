package com.thbs.learningplan.controller;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.service.BatchCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/batch-course")
public class BatchCourseController {

    @Autowired
    private BatchCourseService batchCourseService;

    @PostMapping("/add")
    public BatchCourse addBatchCourse(@RequestBody BatchCourse batchCourse) {
        return batchCourseService.addBatchCourse(batchCourse);
    }

    @PostMapping("/multiple")
    public List<BatchCourse> addMultipleBatchCourses(@RequestBody List<BatchCourse> batchCourses) {
        return batchCourseService.addMultipleBatchCourses(batchCourses);
    }

    @GetMapping
    public List<BatchCourse> getAllBatchCourses() {
        return batchCourseService.getAllBatchCourses();
    }

    @GetMapping("batch/{batchId}")
    public Optional<BatchCourse> getBatchCoursesByBatchId(@PathVariable Long batchId) {
        return batchCourseService.getBatchCoursesByBatchId(batchId);
    }

    @GetMapping("learningPlan/{learningPlanId}")
    public Optional<BatchCourse> getBatchCoursesByLearningPlanId(@PathVariable Long learningPlanId) {
        return batchCourseService.getBatchCoursesByLearningPlanId(learningPlanId);
    }

    @GetMapping("course/{courseId}")
    public Optional<BatchCourse> getBatchCoursesByCourseId(@PathVariable Long courseId) {
        return batchCourseService.getBatchCoursesByCourseId(courseId);
    }

    @PatchMapping("update-trainer/{batchId}")
    public BatchCourse updateTrainerByBatchId(@PathVariable Long batchId, @RequestParam String trainer) {
        return batchCourseService.updateTrainerByBatchId(batchId, trainer);
    }

    @PatchMapping("update-trainer/{courseId}")
    public BatchCourse updateTrainerByCourseId(@PathVariable Long courseId, @RequestParam String trainer) {
        return batchCourseService.updateTrainerByCourseId(courseId, trainer);
    }

    @PatchMapping("/update-trainer/{learningPlanId}")
    public BatchCourse updateTrainerByLearningPlanId(@PathVariable Long learningPlanId, @RequestParam String trainer) {
        return batchCourseService.updateTrainerByLearningPlanId(learningPlanId, trainer);
    }

    @PatchMapping("/update-dates/{batchId}")
    public BatchCourse updateDatesByBatchId(@PathVariable Long batchId, @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return batchCourseService.updateDatesByBatchId(batchId, startDate, endDate);
    }

    @PatchMapping("/update-dates/{courseId}")
    public BatchCourse updateDatesByCourseId(@PathVariable Long courseId, @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return batchCourseService.updateDatesByCourseId(courseId, startDate, endDate);
    }

    @PatchMapping("/update-dates/{learningPlanId}")
    public BatchCourse updateDatesByLearningPlanId(@PathVariable Long learningPlanId, @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return batchCourseService.updateDatesByLearningPlanId(learningPlanId, startDate, endDate);
    }

    @DeleteMapping("/{batchId}")
    public void deleteBatchCourseByBatchId(@PathVariable Long batchId) {
        batchCourseService.deleteBatchCourseByBatchId(batchId);
    }

    @DeleteMapping("/{courseId}")
    public void deleteBatchCourseByCourseId(@PathVariable Long courseId) {
        batchCourseService.deleteBatchCourseByCourseId(courseId);
    }

    @DeleteMapping("/{learningPlanId}")
    public void deleteBatchCourseByLearningPlanId(@PathVariable Long learningPlanId) {
        batchCourseService.deleteBatchCourseByLearningPlanId(learningPlanId);
    }
}
