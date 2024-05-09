package com.thbs.learningplan.testService;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
    void testUpdateTrainer_Success() {
        BatchCourseId batchCourseId = new BatchCourseId();
        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setTrainer("Old Trainer");

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));
        when(batchCourseRepository.save(batchCourse)).thenReturn(batchCourse);

        BatchCourse result = batchCourseService.updateTrainer(batchCourseId, "New Trainer");

        assertNotNull(result);
        assertEquals("New Trainer", result.getTrainer());
    }

    @Test
    void testUpdateTrainer_BatchCourseNotFound() {
        BatchCourseId batchCourseId = new BatchCourseId();

        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> batchCourseService.updateTrainer(batchCourseId, "Trainer"));
    }

    @Test
    void testUpdateTrainer_InvalidDataException() {
        BatchCourseId batchCourseId = new BatchCourseId();

        assertThrows(InvalidDataException.class, () -> batchCourseService.updateTrainer(batchCourseId, null));
        assertThrows(InvalidDataException.class, () -> batchCourseService.updateTrainer(batchCourseId, ""));
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
