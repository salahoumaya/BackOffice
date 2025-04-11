package com.userPI.usersmanagementsystem.service;


import com.userPI.usersmanagementsystem.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course createCourse(Course course, int trainingId);
    Optional<Course> getCourseById(int id);
    List<Course> getAllCourses();
    List<Course> getCoursesByTraining(int trainingId);
    Course updateCourse(int id, Course updatedCourse);
    void deleteCourse(int id);

}
