package com.thbs.learningplan.testService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.thbs.learningplan.exception.FileProcessingException;
import com.thbs.learningplan.exception.InvalidSheetFormatException;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.repository.CourseRepository;
import com.thbs.learningplan.repository.TopicRepository;
import com.thbs.learningplan.service.BulkUploadService;
import com.thbs.learningplan.utility.EmptyRowExcelFileGenerator;
import com.thbs.learningplan.utility.InvalidA1CellExcelGenerator;
import com.thbs.learningplan.utility.InvalidB1CellExcelGenerator;
import com.thbs.learningplan.utility.InvalidExtraDataExcelFileGenerator;
import com.thbs.learningplan.utility.InvalidNoDescriptionFileGenerator;
import com.thbs.learningplan.utility.InvalidOneCellExcelFileGenerator;
import com.thbs.learningplan.utility.MockExcelFileGenerator;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BulkUploadServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private BulkUploadService bulkUploadService;

    @Test
    void testValidExcelFileUpload() throws IOException, FileProcessingException {
        MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();
        // Call the uploadFile method and ensure no exceptions are thrown
        assertDoesNotThrow(() -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testInvalidA1CellExcelFile() throws IOException {
        String filePath = "invalidA1.xlsx";
        MockMultipartFile file = InvalidA1CellExcelGenerator.generateInvalidA1CellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testInvalidB1CellExcelFile() throws IOException {
        String filePath = "invalidB1.xlsx";
        MockMultipartFile file = InvalidB1CellExcelGenerator.generateInvalidB1CellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testInvalidNoDescriptionFile() throws IOException {
        String filePath = "noDesc.xlsx";
        MockMultipartFile file = InvalidNoDescriptionFileGenerator.generateInvalidNoDescriptionFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testInvalidOneCellExcelFile() throws IOException {
        String filePath = "oneColumn.xlsx";
        MockMultipartFile file = InvalidOneCellExcelFileGenerator.generateInvalidOneCellExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testEmptyRowExcelFile() throws IOException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateEmptyRowExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testHandlingMissingTopics() throws IOException, FileProcessingException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateEmptyRowExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testUploadFileWithExistingCourse() throws IOException {
        // Mock behavior of courseRepository.findByCourseName to return a non-empty
        // Optional
        Course existingCourse = new Course();
        existingCourse.setCourseName("Sheet1");
        existingCourse.setLevel("Existing Level");
        when(courseRepository.findByCourseNameIgnoreCase("Sheet1")).thenReturn(Optional.of(existingCourse));

        // Upload a file with a new course
        MockMultipartFile newCourseFile = MockExcelFileGenerator.generateMockExcelFile();
        bulkUploadService.uploadFile(newCourseFile);

        // Verify that no new course is saved (since one already exists with the same
        // name)
        Course savedCourse = verify(courseRepository, never()).save(any(Course.class));
        assertEquals(null, savedCourse);
    }

    @Test
    void testInvalidExtraDataFileUpload() throws Exception {
        String filePath = "extraData.xlsx";
        MockMultipartFile file = InvalidExtraDataExcelFileGenerator.generateInvalidExtraDataExcelFile(filePath);
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

}
