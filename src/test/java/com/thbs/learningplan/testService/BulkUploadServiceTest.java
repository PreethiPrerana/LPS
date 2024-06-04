package com.thbs.learningplan.testService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.thbs.learningplan.exception.DuplicateEntryException;
import com.thbs.learningplan.exception.FileProcessingException;
import com.thbs.learningplan.exception.InvalidSheetFormatException;
import com.thbs.learningplan.repository.CourseRepository;
import com.thbs.learningplan.repository.TopicRepository;
import com.thbs.learningplan.service.BulkUploadService;
import com.thbs.learningplan.utility.DuplicateTopicEntryExcelFile;
import com.thbs.learningplan.utility.EmptyRowExcelFileGenerator;
import com.thbs.learningplan.utility.InvalidA1CellExcelGenerator;
import com.thbs.learningplan.utility.InvalidB1CellExcelGenerator;
import com.thbs.learningplan.utility.InvalidCourseDurationFileGenerator;
import com.thbs.learningplan.utility.MockExcelFileGenerator;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testInvalidCourseDurationExcelFile() throws IOException {
        MockMultipartFile file = InvalidCourseDurationFileGenerator.generateInvalidCourseDurationExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testHandlingMissingTopics() throws IOException, FileProcessingException {
        MockMultipartFile file = EmptyRowExcelFileGenerator.generateNoTopicsExcelFile();
        assertThrows(InvalidSheetFormatException.class, () -> bulkUploadService.uploadFile(file));
    }

    @Test
    void testDuplicateTopicEntry() throws IOException, FileProcessingException {
        MockMultipartFile file = DuplicateTopicEntryExcelFile.generateDuplicateTopicsExcelFile();
        assertThrows(DuplicateEntryException.class, () -> bulkUploadService.uploadFile(file));
    }

}
