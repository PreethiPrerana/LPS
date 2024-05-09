package com.thbs.learningplan.testExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.thbs.learningplan.exception.DuplicateEntryException;
import com.thbs.learningplan.exception.ErrorResponse;
import com.thbs.learningplan.exception.GlobalExceptionHandler;
import com.thbs.learningplan.exception.InvalidDataException;
import com.thbs.learningplan.exception.InvalidSheetFormatException;
import com.thbs.learningplan.exception.NotFoundException;
import com.thbs.learningplan.exception.TemplateDownloadException;

class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleNotFoundException() {
        // Create a mock NotFoundException with a custom message
        NotFoundException mockException = mock(NotFoundException.class);
        String errorMessage = "Learning plan not found";
        when(mockException.getMessage()).thenReturn(errorMessage);

        // Call the exception handler method
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ErrorResponse response = handler.handleNotFoundException(mockException);

        // Verify the HTTP status and error message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidDataException() {
        InvalidDataException mockException = mock(InvalidDataException.class);
        String errorMessage = "Invalid learning plan data";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidDataException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleDuplicateEntryException() {
        DuplicateEntryException mockException = mock(DuplicateEntryException.class);
        String errorMessage = "Duplicate learning plan detected";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleDuplicateEntryException(mockException);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleInvalidSheetFormatException() {
        InvalidSheetFormatException mockException = mock(InvalidSheetFormatException.class);
        String errorMessage = "Invalid Sheet Format";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleInvalidSheetFormatException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleFileUploadException() {
        FileUploadException mockException = mock(FileUploadException.class);
        String errorMessage = "Issue with File Upload";
        when(mockException.getMessage()).thenReturn(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleFileUploadException(mockException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(errorMessage, response.getMessage());
    }

    @Test
    void testHandleTemplateDownloadException() {
        // Create a TemplateDownloadException with a specific message
        TemplateDownloadException exception = new TemplateDownloadException(
                "Error occurred while downloading template");

        // Call the exception handler method
        ErrorResponse errorResponse = globalExceptionHandler.handleTemplateDownloadException(exception);

        // Verify that the returned ErrorResponse has the correct status code and
        // message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getStatus());
        assertEquals("Error occurred while downloading template", errorResponse.getMessage());
    }
}
