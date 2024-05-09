package com.thbs.learningplan.testService;

import com.thbs.learningplan.exception.DuplicateEntryException;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.model.LearningPlan;
import com.thbs.learningplan.repository.LearningPlanRepository;
import com.thbs.learningplan.service.CourseService;
import com.thbs.learningplan.service.LearningPlanService;
import com.thbs.learningplan.service.TopicService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningPlanServiceTest {

    @Mock
    private LearningPlanRepository learningPlanRepository;

    @Mock
    private TopicService topicService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private LearningPlanService learningPlanService;

    private LearningPlan learningPlan;

    @BeforeEach
    void setUp() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanId(1L);
        learningPlan.setType("Test Type");
        learningPlan.setLearningPlanName("Test Learning Plan");
    }

    @Test
    void testSaveLearningPlan() {
        when(learningPlanRepository.save(learningPlan)).thenReturn(learningPlan);

        LearningPlan savedLearningPlan = learningPlanService.saveLearningPlan(learningPlan);

        verify(learningPlanRepository, times(1)).save(learningPlan);
        assertEquals(learningPlan, savedLearningPlan);
    }

    @Test
    void testSaveLearningPlan_InvalidDataException() {
        learningPlan = new LearningPlan();
        learningPlan.setLearningPlanId(1L);
        learningPlan.setType(null);
        learningPlan.setLearningPlanName("Test Learning Plan");

        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });

        learningPlan.setType("");
        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });

        learningPlan.setType("Test Type");
        learningPlan.setLearningPlanName(null);

        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });

        learningPlan.setLearningPlanName("");

        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });
    }

    @Test
    void testSaveLearningPlan_DuplicateEntryException() {
        // Prepare data
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setLearningPlanName("Summer Bootcamp");
        learningPlan.setType("Bootcamp");

        List<LearningPlan> existingLearningPlans = new ArrayList<>();
        existingLearningPlans.add(learningPlan); // Simulating existing learning plan with same name
        when(learningPlanRepository.findByLearningPlanNameIgnoreCase(learningPlan.getLearningPlanName()))
                .thenReturn(existingLearningPlans);

        // Calling the method and verifying exception
        assertThrows(DuplicateEntryException.class, () -> {
            learningPlanService.saveLearningPlan(learningPlan);
        });

        // Verifying that save method was not called
        verify(learningPlanRepository, never()).save(learningPlan);
    }

    @Test
    void testGetAllLearningPlans() {
        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);

        when(learningPlanRepository.findAll()).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getAllLearningPlans();

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    // @Test
    // void testGetAllLearningPlanPathDTOs() {
    // // Sample data
    // LearningPlan learningPlan1 = new LearningPlan();
    // learningPlan1.setLearningPlanId(1L);

    // LearningPlan learningPlan2 = new LearningPlan();
    // learningPlan2.setLearningPlanId(2L);

    // List<LearningPlan> learningPlans = List.of(learningPlan1, learningPlan2);

    // // Mocking repository method
    // when(learningPlanPathRepository.findAll()).thenReturn(List.of(new Module(),
    // new Module()));

    // // Calling the method to test
    // List<LearningPlanDTO> learningPlanDTOs =
    // learningPlanService.getAllLearningPlanPathDTOs();
    // assertNotNull(learningPlans);
    // assertNotNull(learningPlanDTOs);

    // }

    // @Test
    // void testGetAllLearningPlanPathDTOsByBatchId() {
    // // Mock data
    // Long batchId = 123L;
    // List<LearningPlanDTO> allDTO = new ArrayList<>();
    // // Add mock data to allDTO
    // when(learningPlanService.getAllLearningPlanPathDTOs()).thenReturn(allDTO);

    // // Call getAllLearningPlanPathDTOsByBatchId method
    // List<LearningPlanDTO> dtoByBatch =
    // learningPlanService.getAllLearningPlanPathDTOsByBatchId(batchId);

    // // Verify the correctness of dtoByBatch
    // // Add assertions to verify that only DTOs with the correct batchId are
    // returned
    // for (LearningPlanDTO dto : dtoByBatch) {
    // assertEquals(batchId, dto.getBatchId());
    // }
    // }

    @Test
    void testGetLearningPlanById_Success() {
        Long learningPlanId = 1L;

        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.of(learningPlan));

        LearningPlan actualLearningPlan = learningPlanService.getLearningPlanById(learningPlanId);

        assertEquals(learningPlan, actualLearningPlan);
    }

    @Test
    void testGetLearningPlanById_NotFound() {
        Long learningPlanId = 1L;

        when(learningPlanRepository.findById(learningPlanId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            learningPlanService.getLearningPlanById(learningPlanId);
        });
    }

    @Test
    void testGetLearningPlansByType_Success() {
        String type = "Test Type";

        List<LearningPlan> expectedLearningPlans = new ArrayList<>();
        expectedLearningPlans.add(learningPlan);

        when(learningPlanRepository.findByType(type)).thenReturn(expectedLearningPlans);

        List<LearningPlan> actualLearningPlans = learningPlanService.getLearningPlansByType(type);

        assertEquals(expectedLearningPlans.size(), actualLearningPlans.size());
        assertEquals(expectedLearningPlans.get(0), actualLearningPlans.get(0));
    }

    @Test
    void testGetLearningPlansByType_NotFound() {
        String type = "Nonexistent Type";

        when(learningPlanRepository.findByType(type)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> {
            learningPlanService.getLearningPlansByType(type);
        });
    }

    @Test
    void testGetLearningPlansByType_NullType() {
        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.getLearningPlansByType(null);
        });
        assertThrows(InvalidDataException.class, () -> {
            learningPlanService.getLearningPlansByType("");
        });
    }

    @Test
    void testUpdateLearningPlanName_Success() {
        // Prepare data
        Long learningPlanId = 1L;
        String newLearningPlanName = "Updated Name";

        LearningPlan existingLearningPlan = new LearningPlan();
        existingLearningPlan.setLearningPlanName("Original Name");

        LearningPlan newLearningPlan = new LearningPlan();
        newLearningPlan.setLearningPlanName(newLearningPlanName);

        when(learningPlanRepository.findById(learningPlanId))
                .thenReturn(Optional.of(existingLearningPlan));
        when(learningPlanRepository.save(existingLearningPlan)).thenReturn(newLearningPlan);

        // Calling the method
        LearningPlan updatedLearningPlan = learningPlanService.updateLearningPlanName(learningPlanId,
                newLearningPlanName);

        // Verifying
        assertEquals(newLearningPlanName, updatedLearningPlan.getLearningPlanName());
        verify(learningPlanRepository, times(1)).save(existingLearningPlan);
    }

    @Test
    void testUpdateLearningPlanName_NotFoundException() {
        // Prepare data
        Long nonExistingLearningPlanId = 999L;
        String newLearningPlanName = "Updated Name";

        when(learningPlanRepository.findById(nonExistingLearningPlanId))
                .thenReturn(Optional.empty());

        // Calling the method and verifying exception
        assertThrows(NotFoundException.class, () -> {
            learningPlanService.updateLearningPlanName(nonExistingLearningPlanId, newLearningPlanName);
        });

        // Verifying that save method was not called
        verify(learningPlanRepository, never()).save(any());
    }

    @Test
    void testDeleteLearningPlanById() {
        // Mocking
        Long learningPlanIdToDelete = 1L;
        doReturn(true).when(learningPlanRepository).existsById(learningPlanIdToDelete);

        // Calling the method
        learningPlanService.deleteLearningPlanById(learningPlanIdToDelete);

        // Verifying
        verify(learningPlanRepository, times(1)).deleteById(learningPlanIdToDelete);
    }

    @Test
    void testDeleteLearningPlanById_LearningPlanNotFound() {
        Long id = 1L;

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            learningPlanService.deleteLearningPlanById(id);
        });

        assertEquals("Learning plan not found.", exception.getMessage());
    }
}
