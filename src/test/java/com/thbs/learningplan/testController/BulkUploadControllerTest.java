package com.thbs.learningplan.testController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import com.thbs.learningplan.utility.MockExcelFileGenerator;
import com.thbs.learningplan.utility.JPEGFileGenerator;
import com.thbs.learningplan.utility.PDFFileGenerator;
import com.thbs.learningplan.utility.PNGFileGenerator;
import com.thbs.learningplan.utility.TextFileGenerator;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BulkUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFileUploadSuccess() throws Exception {
        MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/course/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded successfully."));
    }

    @Test
    void pdfFileUploadFailure() throws Exception {
        String filePath = "sample.pdf";
        File pdfFile = PDFFileGenerator.generatePDFFile(filePath);

        MockMultipartFile file = PDFFileGenerator.convertToMockMultipartFile(pdfFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/course/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void pngFileUploadFailure() throws Exception {
        String filePath = "sample.png";
        File pngFile = PNGFileGenerator.generatePNGFile(filePath);

        MockMultipartFile file = PNGFileGenerator.convertToMockMultipartFile(pngFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/course/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void textFileUploadFailure() throws Exception {
        String filePath = "sample.txt";
        File textFile = TextFileGenerator.generateTextFile(filePath);

        MockMultipartFile file = TextFileGenerator.convertToMockMultipartFile(textFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/course/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void jpegFileUploadFailure() throws Exception {
        String filePath = "sample.jpeg";
        File jpegFile = JPEGFileGenerator.generateJPEGFile(filePath);

        MockMultipartFile file = JPEGFileGenerator.convertToMockMultipartFile(jpegFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/course/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

     @Test
    void testDownloadExcelFile() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/course/download"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(MediaType.APPLICATION_OCTET_STREAM_VALUE, response.getContentType());
        
        String contentDisposition = response.getHeader("Content-Disposition");
        assertTrue(contentDisposition.contains("filename=\"template.xlsx\""));
    }
}