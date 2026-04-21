package com.example.lmssystem.services;

import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Lesson;
import com.example.lmssystem.mappers.LessonMapper;
import com.example.lmssystem.repositories.ChapterRepository;
import com.example.lmssystem.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private static final Logger log = LoggerFactory.getLogger(LessonService.class);
    private final ChapterRepository chapterRepository;

    public void validateLessonDto(LessonDTO lessonDTO) {
        if (lessonDTO.getName() == null || lessonDTO.getName().trim().length() < 2) {
            log.warn("Invalid lesson name: {}", lessonDTO.getName());
            throw new IllegalArgumentException("Название урока должно содержать минимум 2 символа");
        }
    }

    public LessonDTO createLesson(LessonDTO lessonDTO, Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("Глава с id " + chapterId + " не найдена"));

        this.validateLessonDto(lessonDTO);
        Lesson lesson = lessonMapper.dtoToLesson(lessonDTO);
        lesson.setCreatedTime(LocalDateTime.now());
        lesson.setUpdatedTime(LocalDateTime.now());
        lesson.setChapter(chapter);

        Lesson savedLesson = lessonRepository.save(lesson);
        log.info("Lesson created with id={}, name={}", savedLesson.getId(), savedLesson.getName());
        return lessonMapper.lessonToDto(savedLesson);
    }

    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Урок с id " + id + " не найден"));
        return lessonMapper.lessonToDto(lesson);
    }

    public LessonDTO updateLesson(Long id, LessonDTO lessonDTO) {
        this.validateLessonDto(lessonDTO);
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Урок с id " + id + " не найден"));
        lesson.setUpdatedTime(LocalDateTime.now());
        lesson.setName(lessonDTO.getName());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setContent(lessonDTO.getContent());
        lesson.setLessonOrder(lessonDTO.getLessonOrder());

        Lesson savedLesson = lessonRepository.save(lesson);
        log.info("Lesson updated with id={}, name={}", savedLesson.getId(), savedLesson.getName());
        return lessonMapper.lessonToDto(savedLesson);
    }

    public void deleteLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Урок с id " + id + " не найден"));
        lessonRepository.delete(lesson);
        log.info("Chapter deleted with id={}, name={}", lesson.getId(), lesson.getName());
    }

    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonDTO> lessonDTOS = lessonMapper.lessonsToDto(lessons);
        return lessonDTOS;
    }

    public List<LessonDTO> getByChapterId(Long chapterId) {
        List<Lesson> lessons = lessonRepository.findAllByChapterId(chapterId);
        List<LessonDTO> lessonDTOS = lessonMapper.lessonsToDto(lessons);
        return lessonDTOS;
    }
}
