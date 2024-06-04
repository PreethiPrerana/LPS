package com.thbs.learningplan.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thbs.learningplan.exception.DuplicateEntryException;
import com.thbs.learningplan.exception.FileProcessingException;
import com.thbs.learningplan.model.Course;
import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.repository.CourseRepository;
import com.thbs.learningplan.repository.SubTopicRepository;
import com.thbs.learningplan.repository.TopicRepository;
import com.thbs.learningplan.utility.SheetValidator;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class BulkUploadService {

    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;
    private final SubTopicRepository subTopicRepository;

    @Autowired
    public BulkUploadService(CourseRepository courseRepository, TopicRepository topicRepository, SubTopicRepository subTopicRepository) {
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
        this.subTopicRepository = subTopicRepository;
    }

    @Transactional
    public void uploadFile(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                // Validate the sheet format
                SheetValidator.isValidSheetFormat(sheet);

                // Extract course details from the sheet
                String courseName = sheet.getSheetName();
                Row headerRow = sheet.getRow(0);
                String level = headerRow.getCell(1).getStringCellValue();
                Row durationRow = sheet.getRow(1);
                long courseDuration = (long) durationRow.getCell(1).getNumericCellValue();

                // Check if the course already exists in the database
                Optional<Course> existingCourseOptional = courseRepository.findByCourseNameIgnoreCase(courseName);
                Course course;
                if (existingCourseOptional.isPresent()) {
                    course = existingCourseOptional.get();
                    course.setLevel(level);
                    course.setCourseDuration(courseDuration);
                } else {
                    course = new Course();
                    course.setCourseName(courseName);
                    course.setLevel(level);
                    course.setCourseDuration(courseDuration);
                    course = courseRepository.save(course);
                }

                // Process topics from the sheet
                List<Topic> topics = processTopics(sheet, course);
                topicRepository.saveAll(topics);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileProcessingException("Error processing the uploaded file.");
        }
    }

    private List<Topic> processTopics(Sheet sheet, Course course) {
        List<Topic> topics = new ArrayList<>();
        Set<String> topicNames = new HashSet<>();
        Iterator<Row> iterator = sheet.iterator();

        // Skip header rows
        for (int i = 0; i < 3; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }

        Topic currentTopic = null;
        String currentTopicName = null;
        long currentTopicDuration = 0;

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            if (isRowEmpty(currentRow)) {
                continue;
            }
            try {
                Cell topicCell = currentRow.getCell(0);
                if (topicCell != null && topicCell.getCellType() != CellType.BLANK) {
                    currentTopicName = topicCell.getStringCellValue();
                    currentTopicDuration = (long) currentRow.getCell(2).getNumericCellValue();

                    if (topicNames.contains(currentTopicName)) {
                        throw new DuplicateEntryException("Duplicate entries present in sheet.");
                    }
                    topicNames.add(currentTopicName);

                    if (topicRepository.existsByTopicName(currentTopicName)) {
                        currentTopic = topicRepository.findByTopicNameAndCourse(currentTopicName, course).orElse(null);
                        if (currentTopic != null) {
                            currentTopic.setTopicDuration(currentTopicDuration);
                        }
                    } else {
                        currentTopic = new Topic();
                        currentTopic.setTopicName(currentTopicName);
                        currentTopic.setTopicDuration(currentTopicDuration);
                        currentTopic.setCourse(course);
                        topics.add(currentTopic);
                    }
                }

                if (currentTopic != null) {
                    String subTopicName = currentRow.getCell(1).getStringCellValue();
                    if (!subTopicRepository.existsBySubTopicNameAndTopic(subTopicName, currentTopic)) {
                        SubTopic subTopic = new SubTopic();
                        subTopic.setSubTopicName(subTopicName);
                        subTopic.setTopic(currentTopic);
                        subTopicRepository.save(subTopic); // Save subtopic only if it doesn't exist
                    }
                }

            } catch (DuplicateEntryException e) {
                throw e;
            } catch (Exception e) {
               // Handle extra cells or invalid data by logging the error and continuing
                System.err.println("Error processing row: " + e.getMessage());
            }
        }
        return topics;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
