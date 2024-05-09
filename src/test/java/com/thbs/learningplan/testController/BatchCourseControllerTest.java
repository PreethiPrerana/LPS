package com.thbs.learningplan.testController;

import com.thbs.learningplan.controller.BatchCourseController;
import com.thbs.learningplan.dto.CourseByBatchDTO;
import com.thbs.learningplan.dto.PlanDTO;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.service.BatchCourseService;
import com.thbs.learningplan.utility.DateRange;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
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
        ResponseEntity<String> responseEntity = batchCourseController.updateTrainer(batchCourseId, trainer);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Trainer updated successfully", responseEntity.getBody());
        verify(batchCourseService, times(1)).updateTrainer(batchCourseId, trainer);
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
