package com.example.lmssystem.repositories;

import com.example.lmssystem.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(Long id);

    Optional<Course> findCourseByName(String name);

}
