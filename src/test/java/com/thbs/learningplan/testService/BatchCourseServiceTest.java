package com.thbs.learningplan.testService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.repository.BatchCourseRepository;
import com.thbs.learningplan.repository.LearningPlanRepository;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.utility.DateRange;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchCourseServiceTest {

    @Mock
    private BatchCourseRepository batchCourseRepository;

    @Mock
    private LearningPlanRepository learningPlanRepository;

    @InjectMocks
    private BatchCourseService batchCourseService;

    @Test
    void testAddBatchCourse_Success() {
        // Prepare data
        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setBatchCourseId(new BatchCourseId(1L, new LearningPlan(), new Course()));

        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setCourses(Arrays.asList(new Course())); // Assuming the course is linked to the learning plan

        // Mocking behavior
        when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.of(learningPlan));
        when(batchCourseRepository.save(any())).thenReturn(batchCourse);

        // Call the method
        BatchCourse result = batchCourseService.addBatchCourse(batchCourse);

        // Verify the result
        assertNotNull(result);
        assertEquals(batchCourse, result);
    }

    @Test
    void testAddBatchCourse_Failure_CourseNotLinkedToLearningPlan() {
        // Prepare data
        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setBatchCourseId(new BatchCourseId(1L, new LearningPlan(), new Course()));

        // Mocking behavior
        when(learningPlanRepository.findByLearningPlanId(anyLong())).thenReturn(Optional.empty());

        // Call the method and verify the exception
        assertThrows(NotFoundException.class, () -> {
            batchCourseService.addBatchCourse(batchCourse);
        });
    }

    @Test
    void testConvertToDTO() {
        // Prepare data
        Long batchId = 1L;
        List<BatchCourse> batchCourses = Arrays.asList(
                new BatchCourse(new BatchCourseId(1L, new LearningPlan(), new Course())),
                new BatchCourse(new BatchCourseId(2L, new LearningPlan(), new Course()))
        );

        // Mocking behavior
        when(batchCourseRepository.findByBatchCourseIdBatchId(batchId)).thenReturn(batchCourses);

        // Call the method
        CourseByBatchDTO dto = batchCourseService.convertToDTO(batchId);

        // Verify the result
        assertNotNull(dto);
        assertEquals(batchId, dto.getBatchId());
        assertEquals(2, dto.getCourses().size()); // Assuming there are two batch courses
    }

    @Test
    void testUpdateTrainer_Success() {
        // Prepare data
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());
        String trainer = "John Doe";
        BatchCourse batchCourse = new BatchCourse();
        batchCourse.setTrainer(trainer);

        // Mocking behavior
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));
        when(batchCourseRepository.save(any())).thenReturn(batchCourse);

        // Call the method
        BatchCourse updatedBatchCourse = batchCourseService.updateTrainer(batchCourseId, trainer);

        // Verify the result
        assertNotNull(updatedBatchCourse);
        assertEquals(trainer, updatedBatchCourse.getTrainer());
    }

    @Test
    void testUpdateTrainer_Failure_EmptyTrainer() {
        // Prepare data
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());
        String trainer = ""; // Empty trainer

        // Call the method and verify the exception
        assertThrows(InvalidDataException.class, () -> {
            batchCourseService.updateTrainer(batchCourseId, trainer);
        });
    }

    @Test
    void testUpdateDates_Success() {
        // Prepare data
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());
        DateRange dateRange = new DateRange(batchCourseId, new Date(), new Date());

        // Mocking behavior
        BatchCourse batchCourse = new BatchCourse();
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(batchCourse));
        when(batchCourseRepository.save(any())).thenReturn(batchCourse);

        // Call the method
        BatchCourse updatedBatchCourse = batchCourseService.updateDates(dateRange);

        // Verify the result
        assertNotNull(updatedBatchCourse);
        assertEquals(dateRange.getStartDate(), updatedBatchCourse.getStartDate());
        assertEquals(dateRange.getEndDate(), updatedBatchCourse.getEndDate());
    }

    @Test
    void testDeleteBatchCourse_Success() {
        // Prepare data
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());

        // Mocking behavior
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.of(new BatchCourse()));

        // Call the method
        batchCourseService.deleteBatchCourse(batchCourseId);

        // Verify the method call
        verify(batchCourseRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteBatchCourse_Failure_NotFound() {
        // Prepare data
        BatchCourseId batchCourseId = new BatchCourseId(1L, new LearningPlan(), new Course());

        // Mocking behavior
        when(batchCourseRepository.findById(batchCourseId)).thenReturn(Optional.empty());

        // Call the method and verify the exception
        assertThrows(NotFoundException.class, () -> {
            batchCourseService.deleteBatchCourse(batchCourseId);
        });
    }
}
