package com.thbs.learningplan.utility;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.thbs.learningplan.exception.InvalidSheetFormatException;

/**
 * The {@code SheetValidator} class provides utility methods for validating the
 * format of Excel sheets.
 */
public class SheetValidator {
    // Private constructor to prevent instantiation
    private SheetValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Validates the format of an Excel sheet.
     *
     * @param sheet The Excel sheet to be validated.
     * @return {@code true} if the sheet format is valid, otherwise {@code false}.
     * @throws InvalidSheetFormatException If the sheet format is invalid.
     */
    public static boolean isValidSheetFormat(Sheet sheet) {
        // Check if the sheet has at least one row
        if (sheet.getPhysicalNumberOfRows() < 1) {
            throw new InvalidSheetFormatException("Sheet does not contain any rows.");
        }

        // Check if the first row (header row) has the correct format
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new InvalidSheetFormatException("Header row is missing.");
        }
        // Check if the first cell (A1) contains "Level"
        Cell firstCell = headerRow.getCell(0);
        if (firstCell == null || !firstCell.getStringCellValue().trim().equalsIgnoreCase("Level")) {
            throw new InvalidSheetFormatException("Header cell A1 must contain 'Level'.");
        }

        // Check if the second cell (B1) contains "BASIC", "INTERMEDIATE", or "ADVANCED"
        Cell levelCell = headerRow.getCell(1);
        if (levelCell == null) {
            throw new InvalidSheetFormatException("Sheet must have a level in cell B1.");
        }

        String level = levelCell.getStringCellValue().trim();
        if (!level.equalsIgnoreCase("BASIC") && !level.equalsIgnoreCase("INTERMEDIATE")
                && !level.equalsIgnoreCase("ADVANCED")) {
            throw new InvalidSheetFormatException(
                    "Header cell B1 must contain 'BASIC', 'INTERMEDIATE', or 'ADVANCED'.");
        }

        // Check if the second row (course duration row) has the correct format
        Row courseDurationRow = sheet.getRow(1);
        if (courseDurationRow == null) {
            throw new InvalidSheetFormatException("Course duration row is missing.");
        }
        Cell durationLabelCell = courseDurationRow.getCell(0);
        if (durationLabelCell == null || !durationLabelCell.getStringCellValue().trim().equalsIgnoreCase("Course Duration (in days)")) {
            throw new InvalidSheetFormatException("Header cell A2 must contain 'Course Duration'.");
        }
        Cell durationValueCell = courseDurationRow.getCell(1);
        if (durationValueCell == null || durationValueCell.getCellType() != CellType.NUMERIC) {
            throw new InvalidSheetFormatException("Course duration must be a numeric value in cell B2.");
        }

        // Check if the third row (header row for topics) has the correct format
        Row topicsHeaderRow = sheet.getRow(3);
        if (topicsHeaderRow == null) {
            throw new InvalidSheetFormatException("Topics header row is missing.");
        }
        if (!"Topic".equalsIgnoreCase(getCellValue(topicsHeaderRow.getCell(0)))
                || !"Sub-Topic".equalsIgnoreCase(getCellValue(topicsHeaderRow.getCell(1)))
                || !"Topic Duration (in hours)".equalsIgnoreCase(getCellValue(topicsHeaderRow.getCell(2)))) {
            throw new InvalidSheetFormatException("The third row must contain 'Topic', 'Sub-Topic', and 'Topic Duration (in hours)' headers.");
        }

        int lastRowNum = sheet.getLastRowNum();
        if (sheet.getRow(lastRowNum) == null || 
            sheet.getRow(lastRowNum).getCell(0) == null || 
            sheet.getRow(lastRowNum).getCell(0).getStringCellValue().isEmpty()) {
                throw new InvalidSheetFormatException("No topics found in the course.");
        }


        // Get iterator for the rows in the sheet
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip the header rows
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        // Process each subsequent row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell topicNameCell = row.getCell(0);
            Cell topicDurationCell = row.getCell(2);

            if (topicNameCell != null && topicNameCell.getCellType() != CellType.BLANK) {
                // If topic name is present, topic duration must also be present and numeric
                if (topicDurationCell == null || topicDurationCell.getCellType() != CellType.NUMERIC) {
                    throw new InvalidSheetFormatException("Topic duration must be a numeric value for topic: " + topicNameCell.getStringCellValue());
                }
            }
        }

        return true;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
