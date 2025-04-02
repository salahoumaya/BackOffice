package com.userPI.usersmanagementsystem.repository;

import com.phegondev.usersmanagementsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByTrainingId(int trainingId);

}
