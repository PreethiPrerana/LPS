package com.thbs.learningplan.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseByBatchDTO {

    private Long batchId;
    private List<CourseDTO> courses;

}