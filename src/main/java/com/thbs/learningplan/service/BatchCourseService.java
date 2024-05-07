package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.CourseDTO;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.utility.DateRange;

import java.util.ArrayList;
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

    public BatchCourseDTO convertToDTO(Long batchId) {
        List<BatchCourse> batchCourses = batchCourseRepository.findByBatchCourseIdBatchId(batchId);
        BatchCourseDTO batchCourseDTO = new BatchCourseDTO();
        batchCourseDTO.setBatchId(batchId);

        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (BatchCourse batchCourse : batchCourses) {
            CourseDTO courseDTO = new CourseDTO();
            Course course = batchCourse.getBatchCourseId().getCourse();
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setCourseName(course.getCourseName());
            courseDTOs.add(courseDTO);
        }
        batchCourseDTO.setCourses(courseDTOs);
        return batchCourseDTO;
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

    public BatchCourse updateDates(DateRange dateRange) {
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
