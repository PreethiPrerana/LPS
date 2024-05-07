package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.CourseDTO;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.repository.LearningPlanRepository;
import com.thbs.learningplan.utility.DateRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchCourseService {

    private static final String NOT_FOUND_MSG = "Topic not found.";
    private BatchCourseRepository batchCourseRepository;
    private LearningPlanRepository learningPlanRepository;

    @Autowired
    public BatchCourseService(BatchCourseRepository batchCourseRepository,
            LearningPlanRepository learningPlanRepository) {
        this.batchCourseRepository = batchCourseRepository;
        this.learningPlanRepository = learningPlanRepository;
    }

    public BatchCourse addBatchCourse(BatchCourse batchCourse) {
        Long courseId = batchCourse.getBatchCourseId().getCourse().getCourseId();
        Long learningPlanId = batchCourse.getBatchCourseId().getLearningPlan().getLearningPlanId();

        LearningPlan learningPlan = learningPlanRepository.findByLearningPlanId(learningPlanId)
                .orElseThrow(() -> new NotFoundException("Learning plan not found."));

        // Check if the course is associated with the learning plan
        boolean courseLinkedToLearningPlan = learningPlan.getCourses()
                .stream()
                .anyMatch(course -> course.getCourseId().equals(courseId));

        if (!courseLinkedToLearningPlan) {
            throw new NotFoundException("The course is not linked to the learning plan.");
        }

        return batchCourseRepository.save(batchCourse);
    }

    public List<BatchCourse> addMultipleBatchCourses(List<BatchCourse> batchCourses) {
        List<BatchCourse> batchCourseList = new ArrayList<>();
        for (BatchCourse batchCourse : batchCourses) {
            batchCourseList.add(addBatchCourse(batchCourse));
        }
        return batchCourseList;
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
        if (trainer == null || trainer.isEmpty())
            throw new InvalidDataException("Trainer cannot be null or empty");
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchCourseId);
        if (!optionalBatchCourse.isPresent()) {
            throw new NotFoundException("BatchCourse not found.");
        }
        BatchCourse batchCourse = optionalBatchCourse.get();
        batchCourse.setTrainer(trainer);
        return batchCourseRepository.save(batchCourse);
    }

    public BatchCourse updateDates(DateRange dateRange) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(dateRange.getBatchCourseId());
        if (!optionalBatchCourse.isPresent()) {
            throw new NotFoundException("BatchCourse not found.");
        }
        BatchCourse batchCourse = optionalBatchCourse.get();
        batchCourse.setStartDate(dateRange.getStartDate());
        batchCourse.setEndDate(dateRange.getEndDate());
        return batchCourseRepository.save(batchCourse);
    }

    public void deleteBatchCourse(BatchCourseId batchId) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchId);
        if (optionalBatchCourse.isPresent()) {
            batchCourseRepository.delete(optionalBatchCourse.get());
        } else {
            // Throws exception if topic not found
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }
}
