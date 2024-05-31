package com.thbs.learningplan.testService;

import com.thbs.learningplan.dto.TrainerBatchCourseDTO;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.repository.CourseRepository;
import com.thbs.learningplan.repository.LearningPlanRepository;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.service.CourseService;
import com.thbs.learningplan.service.TopicService;
import com.thbs.learningplan.utility.DateRange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchCourseServiceTest {

    @Mock
    private BatchCourseRepository batchCourseRepository;

    @Mock
    private LearningPlanRepository learningPlanRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseService courseService;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private BatchCourseService batchCourseService;

    @Test
    void testAddBatchCourse_Success() {
        // Prepare data

        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setBatchCourseId(new BatchCourseId(1L, new LearningPlan(), createSampleCourse()));

        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setCourses(Arrays.asList(createSampleCourse())); // Assuming the course is linked to the learning
                                                                      // plan

        // Mocking behavior
        when(learningPlanRepository.findByLearningPlanId(isNull())).thenReturn(Optional.of(learningPlan)); // Adjusted
                                                                                                           // here
        when(batchCourseRepository.save(any())).thenReturn(batchCourse);

        // Call the method
        BatchCourse result = batchCourseService.addBatchCourse(batchCourse);

        // Verify the result
        assertNotNull(result);
        assertEquals(batchCourse, result);
    }

    // Helper method to create a sample Course object with a valid courseId
    private Course createSampleCourse() {
        Course course = new Course();
        course.setCourseId(1L); // Setting a valid courseId
        // Set other properties as needed
        return course;
    }

    @Test
    void testAddBatchCourse_LearningPlanNotFound() {
        // Prepare data
        BatchCourse batchCourse = new BatchCourse();
        BatchCourseId batchCourseId = new BatchCourseId();

        // Mock Course object using the helper method
        Course course = createSampleCourse();
        batchCourseId.setCourse(course); // Set the Course object in BatchCourseId

        // Create a sample LearningPlan object
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setLearningPlanId(1L); // Set a valid learningPlanId
        batchCourseId.setLearningPlan(learningPlan); // Set the LearningPlan object in BatchCourseId

        batchCourse.setBatchCourseId(batchCourseId);

        when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.empty());

        // Assert that NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> batchCourseService.addBatchCourse(batchCourse));
    }

    @Test
    void testAddBatchCourse_CourseNotLinkedToLearningPlan() {
        // Prepare data
        BatchCourse batchCourse = new BatchCourse();
        BatchCourseId batchCourseId = new BatchCourseId();

        // Create a sample Course object
        Course course = new Course();
        course.setCourseId(1L); // Set a valid courseId
        batchCourseId.setCourse(course); // Set the Course object in BatchCourseId

        // Create a sample LearningPlan object without any associated courses
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setLearningPlanId(1L);
        learningPlan.setCourses(Collections.emptyList());
        batchCourseId.setLearningPlan(learningPlan); // Set the LearningPlan object in BatchCourseId

        batchCourse.setBatchCourseId(batchCourseId);

        when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.of(learningPlan));

        // Assert that NotFoundException is thrown
        assertThrows(NotFoundException.class, () -> batchCourseService.addBatchCourse(batchCourse));
    }

    // @Test
    // void testAddMultipleBatchCourses_Success() {
    // // Prepare data
    // LearningPlan learningPlan = new LearningPlan(1L, null, null, null);
    // learningPlan.setCourses(Arrays.asList(createSampleCourse())); // Assuming the
    // course is linked to the learning

    // Course course = new Course(1L, null, null);

    // BatchCourseId batchCourseId = new BatchCourseId(1L, learningPlan, course);
    // batchCourseId.setCourse(course); // Set the Course object in BatchCourseId

    // BatchCourse batchCourse1 = new BatchCourse();
    // batchCourse1.setBatchCourseId(batchCourseId);
    // BatchCourse batchCourse2 = new BatchCourse();
    // batchCourse1.setBatchCourseId(batchCourseId);
    // List<BatchCourse> batchCourses = Arrays.asList(batchCourse1, batchCourse2);

    // // Mock repository behavior
    // when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.of(learningPlan));
    // when(batchCourseRepository.save(batchCourse1)).thenReturn(batchCourse1);
    // when(batchCourseRepository.save(batchCourse2)).thenReturn(batchCourse2);

    // // Call the method
    // List<BatchCourse> result =
    // batchCourseService.addMultipleBatchCourses(batchCourses);

    // // Verify and assert
    // assertEquals(2, result.size());
    // assertEquals(batchCourse1.getBatchCourseId(),
    // result.get(0).getBatchCourseId());
    // assertEquals(batchCourse2.getBatchCourseId(),
    // result.get(1).getBatchCourseId());

    // verify(batchCourseRepository, times(1)).save(batchCourse1);
    // verify(batchCourseRepository, times(1)).save(batchCourse2);
    // }

    @Test
    void testAddMultipleBatchCourses_Success() {
        // Prepare data
        LearningPlan learningPlan = new LearningPlan(1L, "Sample Plan", "Type", null);
        learningPlan.setCourses(Arrays.asList(createSampleCourse())); // Assuming the

        Course course = new Course(1L, "Sample Course", null,1L);

        BatchCourseId batchCourseId1 = new BatchCourseId(1L, learningPlan, course);
        batchCourseId1.setCourse(course); // Set the Course object in BatchCourseId

        BatchCourseId batchCourseId2 = new BatchCourseId(2L, learningPlan, course);
        batchCourseId2.setCourse(course); // Set the Course object in BatchCourseId

        BatchCourse batchCourse1 = new BatchCourse();
        batchCourse1.setBatchCourseId(batchCourseId1);

        BatchCourse batchCourse2 = new BatchCourse();
        batchCourse2.setBatchCourseId(batchCourseId2);

        List<BatchCourse> batchCourses = Arrays.asList(batchCourse1, batchCourse2);

        // Mock repository behavior
        when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.of(learningPlan));
        when(batchCourseRepository.save(batchCourse1)).thenReturn(batchCourse1);
        when(batchCourseRepository.save(batchCourse2)).thenReturn(batchCourse2);

        // Call the method
        List<BatchCourse> result = batchCourseService.addMultipleBatchCourses(batchCourses);

        // Verify and assert
        assertEquals(2, result.size());
        assertEquals(batchCourse1.getBatchCourseId(), result.get(0).getBatchCourseId());
        assertEquals(batchCourse2.getBatchCourseId(), result.get(1).getBatchCourseId());

        verify(batchCourseRepository, times(1)).save(batchCourse1);
        verify(batchCourseRepository, times(1)).save(batchCourse2);
    }

    @Test
    void testGetAllBatchCourses() {
        // Prepare data
        List<BatchCourse> expectedBatchCourses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BatchCourse batchCourse = new BatchCourse();
            // Set properties of the batch course
            expectedBatchCourses.add(batchCourse);
        }

        // Mocking behavior
        when(batchCourseRepository.findAll()).thenReturn(expectedBatchCourses);

        // Call the method
        List<BatchCourse> result = batchCourseService.getAllBatchCourses();

        // Verify the result
        assertNotNull(result);
        assertEquals(expectedBatchCourses.size(), result.size());
        for (int i = 0; i < expectedBatchCourses.size(); i++) {
            assertEquals(expectedBatchCourses.get(i), result.get(i));
        }

        // Verify that findAll method was called
        verify(batchCourseRepository, times(1)).findAll();
    }

    @Test
    void testGetBatchCoursesByTrainerId_Success() {
        // Prepare data
        Long trainerId = 1L;
        Long batchId = 1L;
        Long courseId = 1L;
        String courseName = "Sample Course";
        String trainerName = "Sample Trainer";
        Long courseDuration=1L;

        LearningPlan learningPlan = new LearningPlan(1L, "Sample Plan", "Type", null);
        Course course = new Course(courseId, courseName, null,courseDuration);

        BatchCourseId batchCourseId = new BatchCourseId(batchId, learningPlan, course);

        BatchCourse batchCourse1 = new BatchCourse();
        batchCourse1.setBatchCourseId(batchCourseId);
        batchCourse1.setTrainerId(trainerId);
        batchCourse1.setTrainer(trainerName);
        batchCourse1.setStartDate(new Date());
        batchCourse1.setEndDate(new Date());

        BatchCourse batchCourse2 = new BatchCourse();
        batchCourse2.setBatchCourseId(batchCourseId);
        batchCourse2.setTrainerId(trainerId);
        batchCourse2.setTrainer(trainerName);
        batchCourse2.setStartDate(new Date());
        batchCourse2.setEndDate(new Date());

        List<BatchCourse> batchCourses = Arrays.asList(batchCourse1, batchCourse2);

        // Mock repository behavior
        when(batchCourseRepository.findByTrainerId(trainerId)).thenReturn(batchCourses);

        // Call the method
        List<TrainerBatchCourseDTO> result = batchCourseService.getBatchCoursesByTrainerId(trainerId);

        // Verify and assert
        assertEquals(2, result.size());

        TrainerBatchCourseDTO dto1 = result.get(0);
        assertEquals(batchId, dto1.getBatchId());
        assertEquals(trainerId, dto1.getTrainerId());
        assertEquals(trainerName, dto1.getTrainer());
        assertEquals(courseId, dto1.getCourseId());
        assertEquals(courseName, dto1.getCourseName());
        assertNotNull(dto1.getStartDate());
        assertNotNull(dto1.getEndDate());

        TrainerBatchCourseDTO dto2 = result.get(1);
        assertEquals(batchId, dto2.getBatchId());
        assertEquals(trainerId, dto2.getTrainerId());
        assertEquals(trainerName, dto2.getTrainer());
        assertEquals(courseId, dto2.getCourseId());
        assertEquals(courseName, dto2.getCourseName());
        assertNotNull(dto1.getStartDate());
        assertNotNull(dto1.getEndDate());
    }

    @Test
    void testUpdateTrainer_Success() {
        BatchCourseId batchCourseId = new BatchCourseId();
        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setTrainer("Old Trainer");
        batchCourse.setTrainerId(1L);

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));
        when(batchCourseRepository.save(batchCourse)).thenReturn(batchCourse);

        BatchCourse result = batchCourseService.updateTrainer(batchCourseId, 2L, "New Trainer");

        assertNotNull(result);
        assertEquals("New Trainer", result.getTrainer());
    }

    @Test
    void testUpdateTrainer_BatchCourseNotFound() {
        BatchCourseId batchCourseId = new BatchCourseId();

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> batchCourseService.updateTrainer(batchCourseId, 1L, "Trainer"));
    }

    @Test
    void testUpdateTrainer_InvalidDataException() {
        BatchCourseId batchCourseId = new BatchCourseId();

        assertThrows(InvalidDataException.class, () -> batchCourseService.updateTrainer(batchCourseId, null, null));
        assertThrows(InvalidDataException.class, () -> batchCourseService.updateTrainer(batchCourseId, 1L, ""));
    }

    @Test
    void testUpdateDates_Success() {
        // Prepare data
        LearningPlan learningPlan = new LearningPlan(2L, null, null, null);
        Course course = new Course(3L, null, null,1L);
        BatchCourseId batchCourseId = new BatchCourseId(1L, learningPlan, course);
        Date startDate = new Date();
        Date endDate = new Date();
        DateRange dateRange = new DateRange();
        dateRange.setBatchCourseId(batchCourseId);
        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);

        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setBatchCourseId(batchCourseId);
        batchCourse.setStartDate(new Date()); // Set some initial date
        batchCourse.setEndDate(new Date()); // Set some initial date

        // Mock repository behavior
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));
        when(batchCourseRepository.save(batchCourse)).thenReturn(batchCourse);

        // Call the method
        BatchCourse result = batchCourseService.updateDates(dateRange);

        // Verify and assert
        assertNotNull(result);
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());

        verify(batchCourseRepository, times(1)).findById(batchCourseId);
        verify(batchCourseRepository, times(1)).save(batchCourse);
    }

    @Test
    void testUpdateDates_NotFound() {
        // Prepare data
        LearningPlan learningPlan = new LearningPlan(2L, null, null, null);
        Course course = new Course(3L, null, null,1L);
        BatchCourseId batchCourseId = new BatchCourseId(1L, learningPlan, course);
        Date startDate = new Date();
        Date endDate = new Date();
        DateRange dateRange = new DateRange();
        dateRange.setBatchCourseId(batchCourseId);
        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);

        // Mock repository behavior
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.empty());

        // Call the method and assert exception
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            batchCourseService.updateDates(dateRange);
        });

        assertEquals("BatchCourse not found.", exception.getMessage());

        verify(batchCourseRepository, times(1)).findById(batchCourseId);
        verify(batchCourseRepository, never()).save(any(BatchCourse.class));
    }

    @Test
    void testDeleteBatchCourse_Success() {
        BatchCourseId batchCourseId = new BatchCourseId();
        BatchCourse batchCourse = new BatchCourse();

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));

        assertDoesNotThrow(() -> batchCourseService.deleteBatchCourse(batchCourseId));
    }

    @Test
    void testDeleteBatchCourse_BatchCourseNotFound() {
        BatchCourseId batchCourseId = new BatchCourseId();

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> batchCourseService.deleteBatchCourse(batchCourseId));
    }
}
