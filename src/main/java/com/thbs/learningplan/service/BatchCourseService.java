package com.thbs.learningplan.service;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.repository.BatchCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BatchCourseService {

    @Autowired
    private BatchCourseRepository batchCourseRepository;

    public BatchCourse addBatchCourse(BatchCourse batchCourse) {
        return batchCourseRepository.save(batchCourse);
    }

    public List<BatchCourse> addMultipleBatchCourses(List<BatchCourse> batchCourses) {
        return batchCourseRepository.saveAll(batchCourses);
    }

    public List<BatchCourse> getAllBatchCourses() {
        return batchCourseRepository.findAll();
    }

    public Optional<BatchCourse> getBatchCoursesByBatchId(Long batchId) {
        return batchCourseRepository.findById(batchId);
    }

    public Optional<BatchCourse> getBatchCoursesByLearningPlanId(Long learningPlanId) {
        return batchCourseRepository.findByLearningPlan_LearningPlanId(learningPlanId);
    }

    public Optional<BatchCourse> getBatchCoursesByCourseId(Long courseId) {
        return batchCourseRepository.findByCourse_CourseId(courseId);
    }

    public BatchCourse updateTrainerByBatchId(Long batchId, String trainer) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setTrainer(trainer);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateTrainerByCourseId(Long courseId, String trainer) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findByCourse_CourseId(courseId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setTrainer(trainer);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateTrainerByLearningPlanId(Long learningPlanId, String trainer) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository
                .findByLearningPlan_LearningPlanId(learningPlanId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setTrainer(trainer);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateDatesByBatchId(Long batchId, Date startDate, Date endDate) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setStartDate(startDate);
            batchCourse.setEndDate(endDate);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateDatesByCourseId(Long courseId, Date startDate, Date endDate) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findByCourse_CourseId(courseId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setStartDate(startDate);
            batchCourse.setEndDate(endDate);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateDatesByLearningPlanId(Long learningPlanId, Date startDate, Date endDate) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository
                .findByLearningPlan_LearningPlanId(learningPlanId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setStartDate(startDate);
            batchCourse.setEndDate(endDate);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public void deleteBatchCourseByBatchId(Long batchId) {
        batchCourseRepository.deleteById(batchId);
    }

    public void deleteBatchCourseByCourseId(Long courseId) {
        batchCourseRepository.deleteByCourse_CourseId(courseId);
    }

    public void deleteBatchCourseByLearningPlanId(Long learningPlanId) {
        batchCourseRepository.deleteByLearningPlan_LearningPlanId(learningPlanId);
    }
}
