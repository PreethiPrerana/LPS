package com.thbs.learningplan.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The {@code InvalidA1CellExcelGenerator} class provides a utility method to
 * generate a mock Excel file
 * with an invalid content in cell A1 for testing purposes.
 */
public class InvalidA1CellExcelGenerator {
    // Private constructor to prevent instantiation
    private InvalidA1CellExcelGenerator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a mock Excel file with invalid content in cell A1.
     *
     * @param filePath The file path of the generated Excel file. (Not used in the
     *                 method implementation)
     * @return A {@code MockMultipartFile} representing the generated Excel file.
     * @throws IOException If an I/O error occurs.
     */
    public static MockMultipartFile generateInvalidA1CellExcelFile(String filePath) throws IOException {
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Difficulty");
            headerRow.createCell(1).setCellValue("BASIC");

            // Create top row
            Row dataRow0 = sheet.createRow(2);
            dataRow0.createCell(0).setCellValue("TOPIC");
            dataRow0.createCell(1).setCellValue("DESCRIPTION");

            // Create data rows
            Row dataRow1 = sheet.createRow(3);
            dataRow1.createCell(0).setCellValue("Data1");
            dataRow1.createCell(1).setCellValue("Data1Desc");

            Row dataRow2 = sheet.createRow(4);
            dataRow2.createCell(0).setCellValue("Data2");
            dataRow2.createCell(1).setCellValue("Data2Desc");

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
