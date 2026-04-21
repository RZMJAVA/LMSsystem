package com.example.lmssystem.mappers;

import com.example.lmssystem.dtos.ChapterDTO;
import com.example.lmssystem.dtos.CourseDTO;
import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Course;
import com.example.lmssystem.entities.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO courseToDto(Course course);

    Course dtoToCourse(CourseDTO courseDTO);

    List<CourseDTO> courseToDtoList(List<Course> courses);

}
