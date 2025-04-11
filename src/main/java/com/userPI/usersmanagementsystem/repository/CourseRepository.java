package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Course;
import com.userPI.usersmanagementsystem.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findCoursesByTraining(Training trainingId);

}
