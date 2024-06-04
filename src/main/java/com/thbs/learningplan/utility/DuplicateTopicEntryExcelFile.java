package com.thbs.learningplan.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DuplicateTopicEntryExcelFile {
    // Private constructor to prevent instantiation
    private DuplicateTopicEntryExcelFile() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a mock Excel file with predefined data and returns it as a
     * {@code MockMultipartFile}.
     *
     * @return A {@code MockMultipartFile} representing the generated mock Excel
     *         file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile generateDuplicateTopicsExcelFile() throws IOException {
        // Create a new workbook
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Course 1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Level");
            headerRow.createCell(1).setCellValue("BASIC");

            // Course Duration row
            Row durationRow = sheet.createRow(1);
            durationRow.createCell(0).setCellValue("Course Duration (in days)");
            durationRow.createCell(1).setCellValue(10);

            // Create topic header row
            Row topicsHeaderRow = sheet.createRow(3);
            topicsHeaderRow.createCell(0).setCellValue("Topic");
            topicsHeaderRow.createCell(1).setCellValue("Sub-Topic");
            topicsHeaderRow.createCell(2).setCellValue("Topic Duration (in days)");

            // Create data rows
            Row dataRow1 = sheet.createRow(4);
            dataRow1.createCell(0).setCellValue("Topic 1");
            dataRow1.createCell(1).setCellValue("Subtopic 1a");
            dataRow1.createCell(2).setCellValue(5);

            Row dataRow2 = sheet.createRow(5);
            dataRow2.createCell(0).setCellValue("Topic 1");
            dataRow2.createCell(1).setCellValue("Subtopic 2a");
            dataRow2.createCell(2).setCellValue(5);

            // Write workbook content to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            // Create MockMultipartFile
            return new MockMultipartFile(
                    "file",
                    "mock_excel.xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    content);
        }
    }   
}
