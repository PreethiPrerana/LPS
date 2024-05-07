package com.thbs.learningplan.service;

// import com.thbs.learningplan.model.BatchCourse;
// import com.thbs.learningplan.model.BatchCourseId;
// import com.thbs.learningplan.repository.BatchCourseRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class BatchCourseService {

//     @Autowired
//     private BatchCourseRepository batchCourseRepository;

//     public BatchCourse addBatchCourse(BatchCourse batchCourse) {
//         return batchCourseRepository.save(batchCourse);
//     }

//     public List<BatchCourse> addMultipleBatchCourses(List<BatchCourse> batchCourses) {
//         return batchCourseRepository.saveAll(batchCourses);
//     }

//     public List<BatchCourse> getAllBatchCourses() {
//         return batchCourseRepository.findAll();
//     }

//     public List<BatchCourse> getBatchCoursesByBatchId(Long batchId) {
//         return batchCourseRepository.findByBatchId(batchId);
//     }

//     // public List<BatchCourse> getBatchCoursesByLearningPlanId(Long learningPlanId) {
//     //     return batchCourseRepository.findByLearningPlan_LearningPlanId(learningPlanId);
//     // }

//     // public List<BatchCourse> getBatchCoursesByCourseId(Long courseId) {
//     //     return batchCourseRepository.findByCourse_CourseId(courseId);
//     // }

//     public BatchCourse updateTrainer(BatchCourseId batchId, String trainer) {
//         Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchId);
//         if (optionalBatchCourse.isPresent()) {
//             BatchCourse batchCourse = optionalBatchCourse.get();
//             batchCourse.setTrainer(trainer);
//             return batchCourseRepository.save(batchCourse);
//         }
//         return null;
//     }
    

//     // public BatchCourse updateDates(Long batchId, Date startDate, Date endDate) {
//     //     Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(new BatchCourseId(batchId));
//     //     if (optionalBatchCourse.isPresent()) {
//     //         BatchCourse batchCourse = optionalBatchCourse.get();
//     //         batchCourse.setStartDate(startDate);
//     //         batchCourse.setEndDate(endDate);
//     //         return batchCourseRepository.save(batchCourse);
//     //     }
//     //     return null;
//     // }

//     // public void deleteBatchCourse(Long batchId) {
//     //     batchCourseRepository.deleteById(new BatchCourseId(batchId));
//     // }
// }


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.learningplan.model.BatchCourse;
import com.thbs.learningplan.model.BatchCourseId;
import com.thbs.learningplan.repository.BatchCourseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BatchCourseService {

    @Autowired
    private BatchCourseRepository batchCourseRepository;

    public BatchCourse addBatchCourse(BatchCourse batchCourse) {
        return batchCourseRepository.save(batchCourse);
    }

    public List<BatchCourse> addMultipleBatchCourses(List<BatchCourse> batchCourses) {
        return batchCourseRepository.saveAll(batchCourses);
    }

    public List<BatchCourse> getAllBatchCourses() {
        return batchCourseRepository.findAll();
    }

    // public List<BatchCourse> getBatchCoursesByBatchId(BatchCourseId batchId, BatchCourseId learningPlanId, BatchCourseId courseId) {
    //     return batchCourseRepository.findByBatchCourseId(BatchCourseId batchCourseId);
    // }


    public BatchCourse updateTrainer(BatchCourseId batchCourseId, String trainer) {
        List<BatchCourse> optionalBatchCourse = batchCourseRepository.findByBatchCourseId(batchCourseId);
        for (BatchCourse batchCourse:optionalBatchCourse){
        batchCourse.setTrainer(trainer);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public BatchCourse updateDates(BatchCourseId batchCourseId, Date startDate, Date endDate) {
        Optional<BatchCourse> optionalBatchCourse = batchCourseRepository.findById(batchCourseId);
        if (optionalBatchCourse.isPresent()) {
            BatchCourse batchCourse = optionalBatchCourse.get();
            batchCourse.setStartDate(startDate);
            batchCourse.setEndDate(endDate);
            return batchCourseRepository.save(batchCourse);
        }
        return null;
    }

    public void deleteBatchCourse(BatchCourseId batchId) {
        batchCourseRepository.deleteById(batchId);
    }
}
