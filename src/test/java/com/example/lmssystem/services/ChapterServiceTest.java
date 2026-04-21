package com.example.lmssystem.services;

import com.example.lmssystem.dtos.ChapterDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Course;
import com.example.lmssystem.mappers.ChapterMapper;
import com.example.lmssystem.repositories.ChapterRepository;
import com.example.lmssystem.repositories.CourseRepository;
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
public class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterService chapterService;

    private Chapter chapter;
    private ChapterDTO chapterDTO;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);

        chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("Intro");

        chapterDTO = new ChapterDTO();
        chapterDTO.setId(1L);
        chapterDTO.setName("Intro");
    }

    // ------------------- createChapter -------------------

    @Test
    void createChapter_success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(chapterMapper.dtoToChapter(chapterDTO)).thenReturn(chapter);
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter);
        when(chapterMapper.chapterToDto(chapter)).thenReturn(chapterDTO);

        ChapterDTO result = chapterService.createChapter(chapterDTO, 1L);

        assertNotNull(result);
        assertEquals("Intro", result.getName());
        verify(chapterRepository).save(any(Chapter.class));
    }

    @Test
    void createChapter_courseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                chapterService.createChapter(chapterDTO, 1L)
        );

        verify(chapterRepository, never()).save(any());
    }

    @Test
    void createChapter_invalidName() {
        chapterDTO.setName(" ");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        assertThrows(IllegalArgumentException.class, () ->
                chapterService.createChapter(chapterDTO, 1L)
        );
    }

    // ------------------- getChapterById -------------------

    @Test
    void getChapterById_success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(chapterMapper.chapterToDto(chapter)).thenReturn(chapterDTO);

        ChapterDTO result = chapterService.getChapterById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getChapterById_notFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                chapterService.getChapterById(1L)
        );
    }

    // ------------------- updateChapter -------------------

    @Test
    void updateChapter_success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(chapter);
        when(chapterMapper.chapterToDto(chapter)).thenReturn(chapterDTO);

        ChapterDTO result = chapterService.updateChapter(1L, chapterDTO);

        assertEquals("Intro", result.getName());
        verify(chapterRepository).save(chapter);
    }

    @Test
    void updateChapter_notFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                chapterService.updateChapter(1L, chapterDTO)
        );
    }

    @Test
    void updateChapter_invalidName() {
        chapterDTO.setName("");

        assertThrows(IllegalArgumentException.class, () ->
                chapterService.updateChapter(1L, chapterDTO)
        );
    }

    // ------------------- deleteChapter -------------------

    @Test
    void deleteChapter_success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        chapterService.deleteChapterById(1L);

        verify(chapterRepository).delete(chapter);
    }

    @Test
    void deleteChapter_notFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                chapterService.deleteChapterById(1L)
        );
    }

    // ------------------- getAllChapters -------------------

    @Test
    void getAllChapters_success() {
        List<Chapter> chapters = List.of(chapter);
        List<ChapterDTO> dtos = List.of(chapterDTO);

        when(chapterRepository.findAll()).thenReturn(chapters);
        when(chapterMapper.chaptersToDto(chapters)).thenReturn(dtos);

        List<ChapterDTO> result = chapterService.getAllChapters();

        assertEquals(1, result.size());
    }

    // ------------------- getByCourseId -------------------

    @Test
    void getByCourseId_success() {
        List<Chapter> chapters = List.of(chapter);
        List<ChapterDTO> dtos = List.of(chapterDTO);

        when(chapterRepository.findAllByCourseId(1L)).thenReturn(chapters);
        when(chapterMapper.chaptersToDto(chapters)).thenReturn(dtos);

        List<ChapterDTO> result = chapterService.getByCourseId(1L);

        assertEquals(1, result.size());
    }
}