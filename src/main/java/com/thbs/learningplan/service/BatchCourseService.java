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
import com.thbs.learningplan.repository.BatchCourseRepository;
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
    private TopicService topicService;

    @Autowired
    public BatchCourseService(BatchCourseRepository batchCourseRepository,
            LearningPlanRepository learningPlanRepository, CourseService courseService, TopicService topicService) {
        this.batchCourseRepository = batchCourseRepository;
        this.learningPlanRepository = learningPlanRepository;
        this.courseService = courseService;
        this.topicService = topicService;
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
            Course course = batchCourse.getBatchCourseId().getCourse();
            CourseDTO courseDTO = fetchCourseDTO(course);
            courseDTOs.add(courseDTO);
        }
        batchCourseDTO.setCourses(courseDTOs);
        return batchCourseDTO;
    }

    public PlanDTO generatePlanDTO(Long batchId) {
        // Fetch all BatchCourses associated with the batchId
        List<BatchCourse> batchCourses = batchCourseRepository.findByBatchCourseIdBatchId(batchId);
        PlanDTO planDTO = new PlanDTO();

        if (!batchCourses.isEmpty()) {
            BatchCourse batchCourse = batchCourses.get(0); // Assuming there's only one learning plan per batch course
            LearningPlan learningPlan = batchCourse.getBatchCourseId().getLearningPlan();

            // Populate PlanDTO with LearningPlan details
            planDTO.setBatchId(batchId);
            planDTO.setLearningPlanId(learningPlan.getLearningPlanId());
            planDTO.setLearningPlanName(learningPlan.getLearningPlanName());
            planDTO.setLearningPlanType(learningPlan.getType());
        }

        // Iterate through each BatchCourse and populate BatchCourseDTO
        List<BatchCourseDTO> batchCourseDTOs = new ArrayList<>();
        for (BatchCourse batchCourse : batchCourses) {
            BatchCourseDTO batchCourseDTO = new BatchCourseDTO();
            batchCourseDTO.setStartDate(batchCourse.getStartDate());
            batchCourseDTO.setEndDate(batchCourse.getEndDate());
            batchCourseDTO.setTrainer(batchCourse.getTrainer());

            // Set courseName and courseId
            Course course = batchCourse.getBatchCourseId().getCourse();
            batchCourseDTO.setCourseName(course.getCourseName());
            batchCourseDTO.setCourseId(course.getCourseId());

            List<TopicDTO> topicDTOs = topicService.getTopicsByCourse(course)
                    .stream()
                    .map(topic -> new TopicDTO(topic.getTopicId(), topic.getTopicName()))
                    .collect(Collectors.toList());
            batchCourseDTO.setTopic(topicDTOs);

            batchCourseDTOs.add(batchCourseDTO);
        }

        // Set BatchCourseDTOs in PlanDTO
        planDTO.setBatchCourses(batchCourseDTOs);

        return planDTO;
    }

    private CourseDTO fetchCourseDTO(Course course) {
        // Use CourseService to convert Course entity to CourseDTO
        return courseService.convertToDTO(course);
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
