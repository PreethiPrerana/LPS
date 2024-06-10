package com.thbs.learningplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.dto.BatchCourseDTO;
import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.CourseDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.dto.SubTopicDTO;
import com.thbs.learningplan.dto.TopicDTO;
import com.thbs.learningplan.dto.TrainerBatchCourseDTO;
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
    private SubTopicService subTopicService;

    @Autowired
    public BatchCourseService(BatchCourseRepository batchCourseRepository,
            LearningPlanRepository learningPlanRepository, CourseService courseService, TopicService topicService,
            SubTopicService subTopicService) {
        this.batchCourseRepository = batchCourseRepository;
        this.learningPlanRepository = learningPlanRepository;
        this.courseService = courseService;
        this.topicService = topicService;
        this.subTopicService = subTopicService;
    }

    /**
     * Adds a new batch course.
     * 
     * @param batchCourse The batch course to add.
     * @return The added batch course.
     * @throws NotFoundException if the associated learning plan or course is not
     *                           found.
     */
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

    /**
     * Adds multiple batch courses.
     * 
     * @param batchCourses The list of batch courses to add.
     * @return The list of added batch courses.
     */
    public List<BatchCourse> addMultipleBatchCourses(List<BatchCourse> batchCourses) {
        List<BatchCourse> batchCourseList = new ArrayList<>();
        for (BatchCourse batchCourse : batchCourses) {
            batchCourseList.add(addBatchCourse(batchCourse));
        }
        return batchCourseList;
    }

    /**
     * Retrieves all batch courses.
     * 
     * @return The list of all batch courses.
     */

    public List<BatchCourse> getAllBatchCourses() {
        return batchCourseRepository.findAll();
    }

    /**
     * Converts batch courses to DTO based on batchId.
     * 
     * @param batchId The batch ID.
     * @return The DTO containing batch courses.
     */
    public CourseByBatchDTO convertToDTO(Long batchId) {
        List<BatchCourse> batchCourses = batchCourseRepository.findByBatchCourseIdBatchId(batchId);
        CourseByBatchDTO batchCourseDTO = new CourseByBatchDTO();
        batchCourseDTO.setBatchId(batchId);

        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (BatchCourse batchCourse : batchCourses) {
            Course course = batchCourse.getBatchCourseId().getCourse();
            CourseDTO courseDTO = fetchCourseDTO(course);
            courseDTO.setTopics(null);
            courseDTOs.add(courseDTO);
        }
        batchCourseDTO.setCourses(courseDTOs);
        return batchCourseDTO;
    }

    /**
     * Converts all batch courses to a list of DTOs.
     * 
     * @return A list of DTOs containing batch courses for all batches.
     */
    public List<CourseByBatchDTO> convertAllBatchesToDTO() {
        // Fetch all distinct batch IDs
        List<Long> batchIds = batchCourseRepository.findDistinctBatchIds();

        // Convert each batch ID to CourseByBatchDTO using convertToDTO method and
        // return it
        return batchIds.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all batch courses related to a specific trainer ID and converts
     * them to DTOs.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of {@code TrainerBatchCourseDTO} containing the batch courses
     *         associated with the trainer.
     */
    public List<TrainerBatchCourseDTO> getBatchCoursesByTrainerId(Long trainerId) {
        List<BatchCourse> batchCourses = batchCourseRepository.findByTrainerId(trainerId);

        return batchCourses.stream()
                .map(batchCourse -> new TrainerBatchCourseDTO(
                        batchCourse.getBatchCourseId().getBatchId(),
                        batchCourse.getTrainerId(),
                        batchCourse.getTrainer(),
                        batchCourse.getBatchCourseId().getCourse().getCourseId(), // Assuming BatchCourseId contains
                                                                                  // courseId
                        batchCourse.getBatchCourseId().getCourse().getCourseName(), // Replace with actual course name
                                                                                    // retrieval logic
                        batchCourse.getStartDate(),
                        batchCourse.getEndDate()))
                .collect(Collectors.toList());
    }

    /**
     * Generates plan DTO based on batch ID.
     * 
     * @param batchId The batch ID.
     * @return The generated plan DTO.
     */
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
            batchCourseDTO.setTrainerId(batchCourse.getTrainerId());

            // Set courseName and courseId
            Course course = batchCourse.getBatchCourseId().getCourse();
            batchCourseDTO.setCourseName(course.getCourseName());
            batchCourseDTO.setCourseId(course.getCourseId());

            List<TopicDTO> topicDTOs = topicService.getTopicsByCourse(course)
                    .stream()
                    .map(topic -> {
                        List<SubTopicDTO> subTopicDTOs = subTopicService.getSubTopicsByTopic(topic)
                                .stream()
                                .map(subTopic -> new SubTopicDTO(subTopic.getSubTopicId(), subTopic.getSubTopicName()))
                                .collect(Collectors.toList());
                        return new TopicDTO(topic.getTopicId(), topic.getTopicName(), subTopicDTOs);
                    })
                    .collect(Collectors.toList());
            batchCourseDTO.setTopic(topicDTOs);

            batchCourseDTOs.add(batchCourseDTO);
        }

        // Set BatchCourseDTOs in PlanDTO
        planDTO.setBatchCourses(batchCourseDTOs);

        return planDTO;
    }

    /**
     * Fetches CourseDTO for the given Course.
     * 
     * @param course The Course entity.
     * @return The CourseDTO.
     */
    private CourseDTO fetchCourseDTO(Course course) {
        // Use CourseService to convert Course entity to CourseDTO
        return courseService.convertToDTO(course);
    }

    /**
     * Updates the trainer for a batch course.
     * 
     * @param batchCourseId The ID of the batch course.
     * @param trainer       The new trainer name.
     * @return The updated batch course.
     * @throws InvalidDataException if the trainer name is null or empty.
     * @throws NotFoundException    if the batch course is not found.
     */
    public BatchCourse updateTrainer(BatchCourseId batchCourseId, Long trainerId, String trainer) {
        if (trainer == null || trainer.isEmpty())
            throw new InvalidDataException("Trainer cannot be null or empty");
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchCourseId);
        if (!optionalBatchCourse.isPresent()) {
            throw new NotFoundException("BatchCourse not found.");
        }
        BatchCourse batchCourse = optionalBatchCourse.get();
        batchCourse.setTrainer(trainer);
        batchCourse.setTrainerId(trainerId);
        return batchCourseRepository.save(batchCourse);
    }

    /**
     * Updates the start and end dates for a batch course.
     * 
     * @param dateRange The DateRange object containing batch course ID, start date,
     *                  and end date.
     * @return The updated batch course.
     * @throws NotFoundException if the batch course is not found.
     */
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

    /**
     * Deletes a batch course.
     * 
     * @param batchId The ID of the batch course to delete.
     * @throws NotFoundException if the batch course is not found.
     */
    public void deleteBatchCourse(BatchCourseId batchId) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchId);
        if (optionalBatchCourse.isPresent()) {
            batchCourseRepository.delete(optionalBatchCourse.get());
        } else {
            throw new NotFoundException(NOT_FOUND_MSG);
        }
    }
}
