package com.thbs.learningplan.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.exception.TemplateDownloadException;

import org.springframework.core.io.ResourceLoader;

@Service
public class ExcelDownloadService {

    private final ResourceLoader resourceLoader;

    public ExcelDownloadService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ResponseEntity<Resource> downloadTemplate() {
        try {
            Resource fileResource = resourceLoader.getResource("classpath:template.xlsx");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "template.xlsx");

            return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new TemplateDownloadException("Error occurred while downloading template");
        }
    }
}
