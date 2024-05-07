package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.utility.DateRange;

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

    public BatchCourse updateTrainer(BatchCourseId batchCourseId, String trainer) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchCourseId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setTrainer(trainer);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateDates(/* BatchCourseId batchCourseId, */DateRange dateRange) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(dateRange.getBatchCourseId());
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setStartDate(dateRange.getStartDate());
            batchCourse.setEndDate(dateRange.getEndDate());
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public void deleteBatchCourse(BatchCourseId batchId) {
        batchCourseRepository.deleteByBatchCourseId(batchId);
    }
}
