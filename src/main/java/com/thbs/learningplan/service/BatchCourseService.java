package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.CourseDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.dto.TopicDTO;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.repository.CourseRepository;
import com.thbs.learningplan.repository.LearningPlanRepository;
import com.thbs.learningplan.utility.DateRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BatchCourseService {

    private static final String NOT_FOUND_MSG = "Topic not found.";
    private BatchCourseRepository batchCourseRepository;
    private LearningPlanRepository learningPlanRepository;
    private CourseService courseService;
    private CourseRepository courseRepository;

    @Autowired
    public BatchCourseService(BatchCourseRepository batchCourseRepository,
            LearningPlanRepository learningPlanRepository, CourseRepository courseRepository,
            CourseService courseService) {
        this.batchCourseRepository = batchCourseRepository;
        this.learningPlanRepository = learningPlanRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
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

    public CourseByBatchDTO convertToDTO(Long batchId) {
        List<BatchCourse> batchCourses = batchCourseRepository.findByBatchCourseIdBatchId(batchId);
        CourseByBatchDTO batchCourseDTO = new CourseByBatchDTO();
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

    public PlanDTO generatePlanDTO(Long batchId) {
        // Fetch all BatchCourses associated with the batchId
        List<BatchCourse> batchCourses = batchCourseRepository.findByBatchCourseIdBatchId(batchId);
        PlanDTO planDTO = new PlanDTO();

        for (BatchCourse batchCourse : batchCourses) {
            // Get the LearningPlan ID associated with the BatchCourse
            Long learningPlanId = batchCourse.getBatchCourseId().getLearningPlan().getLearningPlanId();

            // Fetch the LearningPlan details using the LearningPlan ID
            LearningPlan learningPlan = learningPlanRepository.findByLearningPlanId(learningPlanId)
                    .orElseThrow(() -> new NotFoundException("Learning plan not found with id: " + learningPlanId));

            // Populate the PlanDTO with LearningPlan details
            planDTO.setBatchId(batchId);
            planDTO.setLearningPlanId(learningPlanId);
            planDTO.setLearningPlanName(learningPlan.getLearningPlanName());
            planDTO.setLearningPlanType(learningPlan.getType());

            Long courseId = batchCourse.getBatchCourseId().getCourse().getCourseId();

            List<BatchCourseDTO> batchCourseDTOs=new ArrayList<>();
            Optional<Course> optionalCourse=courseRepository.findById(courseId);
            if(optionalCourse.isPresent()){
                Course course= optionalCourse.get();
                courseService.convertToDTO(course);
                BatchCourseDTO batchCourseDTO=new BatchCourseDTO();
                batchCourseDTO.setCourses(null);
                planDTO.setBatchCourses(null);
            }
        }

        // // Fetch the learning plan details associated with the batchCourses
        // Long learningPlanId = batchCourses.get(0).getLearningPlanId(); // Assuming all batch courses have the same
        //                                                                // learning plan
        // LearningPlanDTO learningPlan = learningPlanService.getLearningPlanById(learningPlanId);

        // // Fetch and convert CourseDTOs for each batch course
        // List<CourseDTO> courseDTOs = batchCourses.stream()
        //         .flatMap(batchCourseDTO -> batchCourseDTO.getCourses().stream())
        //         .map(courseService::convertToDTO)
        //         .collect(Collectors.toList());

        // // Create and populate the PlanDTO
        // PlanDTO planDTO = new PlanDTO();
        // planDTO.setBatchId(batchId);
        // planDTO.setLearningPlanId(learningPlanId);
        // planDTO.setLearningPlanName(learningPlan.getLearningPlanName());
        // planDTO.setLearningPlanType(learningPlan.getLearningPlanType());
        // planDTO.setBatchCourses(batchCourses);

        return planDTO;
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
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }
}
