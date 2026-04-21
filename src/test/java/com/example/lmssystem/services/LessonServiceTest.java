package com.example.lmssystem.services;

import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Lesson;
import com.example.lmssystem.mappers.LessonMapper;
import com.example.lmssystem.repositories.ChapterRepository;
import com.example.lmssystem.repositories.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private ChapterRepository chapterRepository;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson;
    private LessonDTO lessonDTO;
    private Chapter chapter;

    @BeforeEach
    void setUp() {
        chapter = new Chapter();
        chapter.setId(1L);

        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setName("Lesson 1");

        lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);
        lessonDTO.setName("Lesson 1");
    }

    // ------------------- createLesson -------------------

    @Test
    void createLesson_success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(lessonMapper.dtoToLesson(lessonDTO)).thenReturn(lesson);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(lessonMapper.lessonToDto(lesson)).thenReturn(lessonDTO);

        LessonDTO result = lessonService.createLesson(lessonDTO, 1L);

        assertNotNull(result);
        assertEquals("Lesson 1", result.getName());
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void createLesson_chapterNotFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                lessonService.createLesson(lessonDTO, 1L)
        );

        verify(lessonRepository, never()).save(any());
    }

    @Test
    void createLesson_invalidName() {
        lessonDTO.setName(" ");

        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        assertThrows(IllegalArgumentException.class, () ->
                lessonService.createLesson(lessonDTO, 1L)
        );
    }

    // ------------------- getLessonById -------------------

    @Test
    void getLessonById_success() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonMapper.lessonToDto(lesson)).thenReturn(lessonDTO);

        LessonDTO result = lessonService.getLessonById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getLessonById_notFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                lessonService.getLessonById(1L)
        );
    }

    // ------------------- updateLesson -------------------

    @Test
    void updateLesson_success() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(lessonMapper.lessonToDto(lesson)).thenReturn(lessonDTO);

        LessonDTO result = lessonService.updateLesson(1L, lessonDTO);

        assertEquals("Lesson 1", result.getName());
        verify(lessonRepository).save(lesson);
    }

    @Test
    void updateLesson_notFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                lessonService.updateLesson(1L, lessonDTO)
        );
    }

    @Test
    void updateLesson_invalidName() {
        lessonDTO.setName("");

        assertThrows(IllegalArgumentException.class, () ->
                lessonService.updateLesson(1L, lessonDTO)
        );
    }

    // ------------------- deleteLesson -------------------

    @Test
    void deleteLesson_success() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        lessonService.deleteLessonById(1L);

        verify(lessonRepository).delete(lesson);
    }

    @Test
    void deleteLesson_notFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                lessonService.deleteLessonById(1L)
        );
    }

    // ------------------- getAllLessons -------------------

    @Test
    void getAllLessons_success() {
        List<Lesson> lessons = List.of(lesson);
        List<LessonDTO> dtos = List.of(lessonDTO);

        when(lessonRepository.findAll()).thenReturn(lessons);
        when(lessonMapper.lessonsToDto(lessons)).thenReturn(dtos);

        List<LessonDTO> result = lessonService.getAllLessons();

        assertEquals(1, result.size());
    }

    // ------------------- getByChapterId -------------------

    @Test
    void getByChapterId_success() {
        List<Lesson> lessons = List.of(lesson);
        List<LessonDTO> dtos = List.of(lessonDTO);

        when(lessonRepository.findAllByChapterId(1L)).thenReturn(lessons);
        when(lessonMapper.lessonsToDto(lessons)).thenReturn(dtos);

        List<LessonDTO> result = lessonService.getByChapterId(1L);

        assertEquals(1, result.size());
    }
}