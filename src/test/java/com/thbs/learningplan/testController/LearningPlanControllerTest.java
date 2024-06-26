package com.thbs.learningplan.testController;

import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.service.BulkUploadService;
import com.thbs.learningplan.service.LearningPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.thbs.learningplan.controller.LearningPlanController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningPlanControllerTest {

    @Mock
    private LearningPlanService learningPlanService;

    @Mock
    private BulkUploadService bulkUploadService;

    @InjectMocks
    private LearningPlanController learningPlanController;

    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanId(1L);
        learningPlan.setLearningPlanName("Test");
        learningPlan.setType("Test Type");
    }

    @Test
    void testSaveLearningPlan() {
        when(learningPlanService.saveLearningPlan(learningPlan)).thenReturn(learningPlan);

        ResponseEntity<?> responseEntity = learningPlanController.saveLearningPlan(learningPlan);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlan, responseEntity.getBody());
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Test Type 2");

        learningPlans.add(learningPlan);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getAllLearningPlans()).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getAllLearningPlans();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    @Test
    void testGetLearningPlanById() {
        // Mocking the LearningPlanService
        LearningPlanService learningPlanService = mock(LearningPlanService.class);

        // Creating a test learning plan
        Long learningPlanId = 1L;
        LearningPlan expectedLearningPlan = new LearningPlan();
        expectedLearningPlan.setLearningPlanId(learningPlanId);
        expectedLearningPlan.setLearningPlanName("Test Learning Plan");

        // Stubbing the behavior of learningPlanService.getLearningPlanById()
        when(learningPlanService.getLearningPlanById(learningPlanId)).thenReturn(expectedLearningPlan);

        // Creating the LearningPlanController instance with the mocked LearningPlanService
        LearningPlanController learningPlanController = new LearningPlanController(learningPlanService);

        // Invoking the getLearningPlanById() method
        ResponseEntity<LearningPlan> response = learningPlanController.getLearningPlanById(learningPlanId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLearningPlan, response.getBody());

        // Verifying that the learningPlanService.getLearningPlanById() was called with the provided ID
        verify(learningPlanService, times(1)).getLearningPlanById(learningPlanId);
    }

    // @Test
    // void testGetAllLearningPlanPathDTOs() {
    //     // Sample data
    //     List<LearningPlanDTO> expectedDTOs = Arrays.asList(
    //             new LearningPlanDTO(),
    //             new LearningPlanDTO());

    //     // Mock service method to return sample data
    //     when(learningPlanService.getAllLearningPlanPathDTOs()).thenReturn(expectedDTOs);

    //     // Call the controller method
    //     ResponseEntity<List<LearningPlanDTO>> response = learningPlanController.getAllLearningPlanPathDTOs();

    //     // Verify response status code
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(expectedDTOs, response.getBody());
    // }

    // @Test
    // void testGetAllLearningPlanPathDTOsByBatchId() {
    //     // Mock data
    //     Long batchId = 123L;
    //     List<LearningPlanDTO> expectedDtos = new ArrayList<>();
    //     expectedDtos.add(new LearningPlanDTO());

    //     // Mock service method
    //     when(learningPlanService.getAllLearningPlanPathDTOsByBatchId(batchId)).thenReturn(expectedDtos);

    //     // Call controller method
    //     ResponseEntity<List<LearningPlanDTO>> response = learningPlanController
    //             .getAllLearningPlanPathDTOsByBatchId(batchId);

    //     // Verify response
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(expectedDtos, response.getBody());
    // }

    @Test
    void testFindByType() {
        String type = "Type";

        List<LearningPlan> learningPlans = new ArrayList<>();

        LearningPlan learningPlan1 = new LearningPlan();
        learningPlan1.setType("Type");

        LearningPlan learningPlan2 = new LearningPlan();
        learningPlan2.setType("Type");

        learningPlans.add(learningPlan1);
        learningPlans.add(learningPlan2);

        when(learningPlanService.getLearningPlansByType(type)).thenReturn(learningPlans);

        ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByType(type);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(learningPlans, responseEntity.getBody());
    }

    // @Test
    // void testFindByBatchID() {
    //     Long batchID = 123L;

    //     List<LearningPlan> learningPlans = new ArrayList<>();

    //     LearningPlan learningPlan1 = new LearningPlan();
    //     learningPlan1.setType("Type 1");

    //     LearningPlan learningPlan2 = new LearningPlan();
    //     learningPlan2.setType("Type 2");

    //     learningPlans.add(learningPlan1);
    //     learningPlans.add(learningPlan2);

    //     when(learningPlanService.getLearningPlanByBatchId(batchID)).thenReturn(learningPlans);

    //     ResponseEntity<?> responseEntity = learningPlanController.getLearningPlansByBatchID(batchID);

    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(learningPlans, responseEntity.getBody());
    // }

    @Test
    void testUpdateLearningPlanName() {
        // Mocking the LearningPlanService
        LearningPlanService learningPlanService = mock(LearningPlanService.class);

        // Creating a test learning plan
        Long learningPlanId = 1L;
        LearningPlan originalLearningPlan = new LearningPlan();
        originalLearningPlan.setLearningPlanId(learningPlanId);
        originalLearningPlan.setLearningPlanName("Original Learning Plan Name");

        // Creating a new learning plan name
        String newLearningPlanName = "Updated Learning Plan Name";

        // Stubbing the behavior of learningPlanService.updateLearningPlanName()
        LearningPlan updatedLearningPlan = new LearningPlan();
        updatedLearningPlan.setLearningPlanId(learningPlanId);
        updatedLearningPlan.setLearningPlanName(newLearningPlanName);
        when(learningPlanService.updateLearningPlanName(learningPlanId, newLearningPlanName))
                .thenReturn(updatedLearningPlan);

        // Creating the LearningPlanController instance with the mocked LearningPlanService
        LearningPlanController learningPlanController = new LearningPlanController(learningPlanService);

        // Invoking the updateLearningPlanName() method
        ResponseEntity<LearningPlan> response = learningPlanController.updateLearningPlanName(learningPlanId,
                newLearningPlanName);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedLearningPlan, response.getBody());

        // Verifying that the learningPlanService.updateLearningPlanName() was called with the provided ID and new name
        verify(learningPlanService, times(1)).updateLearningPlanName(learningPlanId, newLearningPlanName);
    }

    @Test
    void testDeleteLearningPlanById() {
        // Mocking the LearningPlanService
        LearningPlanService learningPlanService = mock(LearningPlanService.class);

        // Creating a test learning plan ID
        Long learningPlanId = 1L;

        // Creating the LearningPlanController instance with the mocked LearningPlanService
        LearningPlanController learningPlanController = new LearningPlanController(learningPlanService);

        // Invoking the deleteLearningPlanById() method
        ResponseEntity<String> response = learningPlanController.deleteLearningPlanById(learningPlanId);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Learning Plan deleted successfully", response.getBody());

        // Verifying that the learningPlanService.deleteLearningPlanById() was called with the provided ID
        verify(learningPlanService, times(1)).deleteLearningPlanById(learningPlanId);
    }
}
