package com.thbs.learningplan.utility;

import java.util.Date;

import com.thbs.learningplan.model.BatchCourseId;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code DateRange} class represents a range of dates with a start date and
 * an end date.
 */
@Getter
@Setter
public class DateRange {

    private BatchCourseId batchCourseId;
    /**
     * The start date of the date range.
     */
    private Date startDate;

    /**
     * The end date of the date range.
     */
    private Date endDate;
}
