package com.example.lmssystem.mappers;

import com.example.lmssystem.dtos.ChapterDTO;
import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ChapterMapper {

    ChapterDTO chapterToDto(Chapter chapter);

    Chapter dtoToChapter(ChapterDTO chapterDTO);

    List<ChapterDTO> chaptersToDto(List<Chapter> chapters);

    List<LessonDTO> lessonsToDto(List<Lesson> lessons);

    LessonDTO lessonToDto(Lesson lesson);

}
