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
public class PlanDTO {
    private Long batchId;
    private Long learningPlanId;
    private String learningPlanName;
    private String learningPlanType;
    private List<BatchCourseDTO> batchCourses;
}
