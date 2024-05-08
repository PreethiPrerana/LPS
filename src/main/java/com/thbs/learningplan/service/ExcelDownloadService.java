package com.thbs.learningplan.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.exception.TemplateDownloadException;

import org.springframework.core.io.ResourceLoader;

/**
 * Service class responsible for downloading Excel templates.
 */
@Service
public class ExcelDownloadService {

    private final ResourceLoader resourceLoader;

     /**
     * Constructor for ExcelDownloadService.
     * 
     * @param resourceLoader The ResourceLoader to load the Excel template file.
     */
    public ExcelDownloadService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Downloads the Excel template file.
     * 
     * @return ResponseEntity containing the Excel template file as a Resource.
     * @throws TemplateDownloadException if an error occurs during the download process.
     */
    public ResponseEntity<Resource> downloadTemplate() {
        try {
            // Load the Excel template file
            Resource fileResource = resourceLoader.getResource("classpath:template.xlsx");

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "template.xlsx");

            // Return ResponseEntity with the Excel template file and headers
            return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions that occur during template download
            throw new TemplateDownloadException("Error occurred while downloading template");
        }
    }
}
