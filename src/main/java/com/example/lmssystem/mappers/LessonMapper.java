package com.example.lmssystem.mappers;

import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.entities.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson dtoToLesson(LessonDTO lessonDTO);

    LessonDTO lessonToDto(Lesson lesson);

    List<LessonDTO> lessonsToDto(List<Lesson> lessons);

    List<Lesson> dtosToLessons(List<LessonDTO> dtos);

}
