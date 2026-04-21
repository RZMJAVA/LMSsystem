package com.example.lmssystem.services;

import com.example.lmssystem.dtos.CourseDTO;
import com.example.lmssystem.entities.Course;
import com.example.lmssystem.mappers.CourseMapper;
import com.example.lmssystem.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);


    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    private void validateCourse(CourseDTO courseDTO) {
        if (courseDTO.getName() == null || courseDTO.getName().trim().length() < 2) {
            log.warn("Invalid course name: {}", courseDTO.getName());
            throw new IllegalArgumentException("Название курса должно содержать минимум 2 символа");
        }
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        this.validateCourse(courseDTO);
        Course course = courseMapper.dtoToCourse(courseDTO);
        course.setCreatedTime(LocalDateTime.now());
        course.setUpdatedTime(LocalDateTime.now());
        Course savedCourse = courseRepository.save(course);
        log.info("Course created with id={}, name={}", savedCourse.getId(), savedCourse.getName());
        return courseMapper.courseToDto(savedCourse);
    }

    public CourseDTO getCourseById(Long id) {
       Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Курс с id " + id + " не найден"));
       CourseDTO courseDTO = courseMapper.courseToDto(course);
       return courseDTO;
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        this.validateCourse(courseDTO);
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Курс с id " + id + " не найден"));
        course.setUpdatedTime(LocalDateTime.now());
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        Course savedCourse = courseRepository.save(course);
        log.info("Course updated with id={}, name={}", savedCourse.getId(), savedCourse.getName());
        return courseMapper.courseToDto(savedCourse);
    }

    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Курс с id " + id + " не найден"));
        courseRepository.delete(course);
        log.info("Course deleted with id={}, name={}", course.getId(), course.getName());
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = courseMapper.courseToDtoList(courses);
        return courseDTOS;
    }

}