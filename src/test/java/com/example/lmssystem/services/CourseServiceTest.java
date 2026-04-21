package com.example.lmssystem.services;

import com.example.lmssystem.dtos.CourseDTO;
import com.example.lmssystem.entities.Course;
import com.example.lmssystem.mappers.CourseMapper;
import com.example.lmssystem.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.lmssystem.dtos.CourseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setName("Java");

        courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Java");
    }

    // ------------------- addCourse -------------------

    @Test
    void addCourse_success() {
        when(courseMapper.DtoToCourse(courseDTO)).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.courseToDto(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.createCourse(courseDTO);

        assertNotNull(result);
        assertEquals("Java", result.getName());

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void addCourse_invalidName_shouldThrowException() {
        courseDTO.setName(" ");

        assertThrows(IllegalArgumentException.class, () ->
                courseService.createCourse(courseDTO)
        );

        verify(courseRepository, never()).save(any());
    }

    // ------------------- getCourseById -------------------

    @Test
    void getCourseById_success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.courseToDto(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.getCourseById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getCourseById_notFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                courseService.getCourseById(1L)
        );
    }

    // ------------------- updateCourse -------------------

    @Test
    void updateCourse_success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.courseToDto(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.updateCourse(1L, courseDTO);

        assertEquals("Java", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void updateCourse_notFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                courseService.updateCourse(1L, courseDTO)
        );
    }

    @Test
    void updateCourse_invalidName() {
        courseDTO.setName("");

        assertThrows(IllegalArgumentException.class, () ->
                courseService.updateCourse(1L, courseDTO)
        );
    }

    // ------------------- deleteCourseById -------------------

    @Test
    void deleteCourse_success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.deleteCourseById(1L);

        verify(courseRepository).delete(course);
    }

    @Test
    void deleteCourse_notFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                courseService.deleteCourseById(1L)
        );
    }

    // ------------------- getAllCourses -------------------

    @Test
    void getAllCourses_success() {
        List<Course> courses = List.of(course);
        List<CourseDTO> dtos = List.of(courseDTO);

        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapper.courseToDtoList(courses)).thenReturn(dtos);

        List<CourseDTO> result = courseService.getAllCourses();

        assertEquals(1, result.size());
    }
}
