package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Course;
import com.userPI.usersmanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/moderator/course/{trainingId}")
    public ResponseEntity<Course> createCourse(@PathVariable int trainingId, @Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course, trainingId));
    }

    @GetMapping("/moderator/allcourses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/public-training/training/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/public-training/training/{trainingId}/courses")
    public ResponseEntity<List<Course>> getCoursesByTraining(@PathVariable int trainingId) {
        return ResponseEntity.ok(courseService.getCoursesByTraining(trainingId));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/moderator/training/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @Valid @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/moderator/training/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
