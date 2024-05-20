package com.thbs.learningplan.testController;

import com.thbs.learningplan.controller.BatchCourseController;
import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.dto.TrainerBatchCourseDTO;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.utility.DateRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchCourseControllerTest {

    @Mock
    private BatchCourseService batchCourseService;

    @InjectMocks
    private BatchCourseController batchCourseController;

    @Test
    void testAddBatchCourse() {
        BatchCourse batchCourse = new BatchCourse();
        when(batchCourseService.addBatchCourse(batchCourse)).thenReturn(batchCourse);

        ResponseEntity<BatchCourse> responseEntity = batchCourseController.addBatchCourse(batchCourse);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchCourse, responseEntity.getBody());
    }

    @Test
    void testAddMultipleBatchCourses() {
        List<BatchCourse> batchCourses = Arrays.asList(new BatchCourse(), new BatchCourse());
        when(batchCourseService.addMultipleBatchCourses(batchCourses)).thenReturn(batchCourses);

        ResponseEntity<List<BatchCourse>> responseEntity = batchCourseController.addMultipleBatchCourses(batchCourses);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchCourses, responseEntity.getBody());
    }

    @Test
    void testGetAllBatchCourses() {
        List<BatchCourse> batchCourses = Arrays.asList(new BatchCourse(), new BatchCourse());
        when(batchCourseService.getAllBatchCourses()).thenReturn(batchCourses);

        ResponseEntity<List<BatchCourse>> responseEntity = batchCourseController.getAllBatchCourses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchCourses, responseEntity.getBody());
    }

    @Test
    void testGetBatchCoursesByTrainerId() {
        // Prepare data
        Long trainerId = 1L;
        Long batchId = 1L;
        Long courseId = 1L;
        String courseName = "Sample Course";
        String trainerName = "Sample Trainer";

        TrainerBatchCourseDTO dto1 = new TrainerBatchCourseDTO(
                batchId, trainerId, trainerName, courseId, courseName, new Date(), new Date());
        TrainerBatchCourseDTO dto2 = new TrainerBatchCourseDTO(
                batchId, trainerId, trainerName, courseId, courseName, new Date(), new Date());
        List<TrainerBatchCourseDTO> batchCourses = Arrays.asList(dto1, dto2);

        // Mock service behavior
        when(batchCourseService.getBatchCoursesByTrainerId(anyLong())).thenReturn(batchCourses);

        // Call the method
        ResponseEntity<List<TrainerBatchCourseDTO>> responseEntity = batchCourseController
                .getBatchCoursesByTrainerId(trainerId);

        // Verify and assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchCourses, responseEntity.getBody());
    }

    @Test
    void testGetBatchCourseDTO() {
        Long batchId = 1L;
        CourseByBatchDTO dto = new CourseByBatchDTO();
        when(batchCourseService.convertToDTO(batchId)).thenReturn(dto);

        ResponseEntity<CourseByBatchDTO> responseEntity = batchCourseController.getBatchCourseDTO(batchId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    void testGetPlanDTO() {
        Long batchId = 1L;
        PlanDTO dto = new PlanDTO();
        when(batchCourseService.generatePlanDTO(batchId)).thenReturn(dto);

        ResponseEntity<PlanDTO> responseEntity = batchCourseController.getPlanDTO(batchId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dto, responseEntity.getBody());
    }

    @Test
    void testUpdateTrainer() {
        BatchCourseId batchCourseId = new BatchCourseId();
        String trainer = "John Doe";
        Long trainerId = 1L;
        ResponseEntity<String> responseEntity = batchCourseController.updateTrainer(batchCourseId, trainer, trainerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Trainer updated successfully", responseEntity.getBody());
        verify(batchCourseService, times(1)).updateTrainer(batchCourseId, trainerId, trainer);
    }

    @Test
    void testUpdateDates() {
        DateRange dateRange = new DateRange();
        ResponseEntity<String> responseEntity = batchCourseController.updateDates(dateRange);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Dates updated Successfully", responseEntity.getBody());
        verify(batchCourseService, times(1)).updateDates(dateRange);
    }

    @Test
    void testDeleteBatchCourse() {
        BatchCourseId batchCourseId = new BatchCourseId();
        ResponseEntity<String> responseEntity = batchCourseController.deleteBatchCourse(batchCourseId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deleted Successfully", responseEntity.getBody());
        verify(batchCourseService, times(1)).deleteBatchCourse(batchCourseId);
    }
}
