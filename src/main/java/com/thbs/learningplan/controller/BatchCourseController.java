package com.thbs.learningplan.controller;
import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.model.LearningPlan; 
import com.thbs.learningplan.model.Course; 
import com.thbs.learningplan.service.BatchCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/batchcourses")
public class BatchCourseController {

    @Autowired
    private BatchCourseService batchCourseService;

    @PostMapping("/add")
    public ResponseEntity<BatchCourse> addBatchCourse(@RequestBody BatchCourse batchCourse) {
        BatchCourse addedBatchCourse = batchCourseService.addBatchCourse(batchCourse);
        return new ResponseEntity<>(addedBatchCourse, HttpStatus.CREATED);
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<List<BatchCourse>> addMultipleBatchCourses(@RequestBody List<BatchCourse> batchCourses) {
        List<BatchCourse> addedBatchCourses = batchCourseService.addMultipleBatchCourses(batchCourses);
        return new ResponseEntity<>(addedBatchCourses, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BatchCourse>> getAllBatchCourses() {
        List<BatchCourse> batchCourses = batchCourseService.getAllBatchCourses();
        return new ResponseEntity<>(batchCourses, HttpStatus.OK);
    }

//     @GetMapping("/byBatchCourseId")
// public ResponseEntity<List<BatchCourse>> getBatchCoursesByBatchId(
//         @RequestParam("batchId") Long batchId,
//         @RequestParam("learningPlanId") Long learningPlanId,
//         @RequestParam("courseId") Long courseId) {

//     List<BatchCourse> batchCourses = batchCourseService.findByBatchCourseId(BatchCourseId batchCourseId);
//     return new ResponseEntity<>(batchCourses, HttpStatus.OK);
// }


@PutMapping("/updateTrainer")
public ResponseEntity<BatchCourse> updateTrainer(@RequestBody BatchCourseId batchCourseId,
        @RequestParam String trainer) {
    BatchCourse updatedBatchCourse = batchCourseService.updateTrainer(batchCourseId, trainer);
    // if (updatedBatchCourse != null) {
        return new ResponseEntity<>(updatedBatchCourse, HttpStatus.OK);
    // } else {
    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
}

@PutMapping("/updateDates")
public ResponseEntity<BatchCourse> updateDates(@RequestBody BatchCourseId batchCourseId,
        @RequestParam Date startDate, @RequestParam Date endDate) {
    BatchCourse updatedBatchCourse = batchCourseService.updateDates(batchCourseId, startDate, endDate);
    if (updatedBatchCourse != null) {
        return new ResponseEntity<>(updatedBatchCourse, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping("/delete/{batchId}")
    public ResponseEntity<Void> deleteBatchCourse(@PathVariable BatchCourseId batchId) {
        batchCourseService.deleteBatchCourse(batchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
