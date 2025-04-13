package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Course;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.repository.CourseRepository;
import com.userPI.usersmanagementsystem.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Course createCourse(Course course, int trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found!"));

        course.setTraining(training);
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByTraining(int trainingId) {
        Training training = trainingRepository.findById(trainingId).get();

        return courseRepository.findCoursesByTraining(training);
    }

    @Override
    public Course updateCourse(int id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setContent(updatedCourse.getContent());
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}
