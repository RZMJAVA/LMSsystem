package com.example.lmssystem.services;

import com.example.lmssystem.dtos.ChapterDTO;
import com.example.lmssystem.entities.Chapter;
import com.example.lmssystem.entities.Course;
import com.example.lmssystem.mappers.ChapterMapper;
import com.example.lmssystem.repositories.ChapterRepository;
import com.example.lmssystem.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private static final Logger log = LoggerFactory.getLogger(ChapterService.class);
    private final ChapterMapper chapterMapper;

    public ChapterService(ChapterRepository chapterRepository, CourseRepository courseRepository, ChapterMapper chapterMapper) {
        this.chapterRepository = chapterRepository;
        this.courseRepository = courseRepository;
        this.chapterMapper = chapterMapper;
    }

    public void validateChapterDTO(ChapterDTO chapterDTO) {
        if(chapterDTO.getName() == null || chapterDTO.getName().trim().length() < 2) {
            log.warn("Invalid course name: {}", chapterDTO.getName());
            throw new IllegalArgumentException("Название курса должно содержать минимум 2 символа");
        }
    }

    public ChapterDTO createChapter(ChapterDTO chapterDTO, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Курс с id " + courseId + " не найден"));

        this.validateChapterDTO(chapterDTO);
        Chapter chapter = chapterMapper.dtoToChapter(chapterDTO);
        chapter.setCreatedTime(LocalDateTime.now());
        chapter.setUpdatedTime(LocalDateTime.now());
        chapter.setCourse(course);

        Chapter savedChapter = chapterRepository.save(chapter);
        log.info("Chapter created with id={}, name={}", savedChapter.getId(), savedChapter.getName());
        return chapterMapper.chapterToDto(savedChapter);
    }

    public ChapterDTO getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Глава с id " + id + " не найдена"));
        return chapterMapper.chapterToDto(chapter);
    }

    public ChapterDTO updateChapter(Long id, ChapterDTO chapterDTO) {
        this.validateChapterDTO(chapterDTO);
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Глава с id " + id + " не найдена"));
        chapter.setUpdatedTime(LocalDateTime.now());
        chapter.setChapterOrder(chapterDTO.getChapterOrder());
        chapter.setName(chapterDTO.getName());
        chapter.setDescription(chapterDTO.getDescription());

        Chapter savedChapter = chapterRepository.save(chapter);
        log.info("Chapter updated with id={}, name={}", savedChapter.getId(), savedChapter.getName());
        return chapterMapper.chapterToDto(savedChapter);
    }

    public void deleteChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Глава с id " + id + " не найдена"));
        chapterRepository.delete(chapter);
        log.info("Chapter deleted with id={}, name={}", chapter.getId(), chapter.getName());
    }

    public List<ChapterDTO> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        List<ChapterDTO> chapterDTOS = chapterMapper.chaptersToDto(chapters);
        return chapterDTOS;
    }

    public List<ChapterDTO> getByCourseId(Long courseId) {
        List<Chapter> chapters = chapterRepository.findAllByCourseId(courseId);
        List<ChapterDTO> chapterDTOS = chapterMapper.chaptersToDto(chapters);
        return chapterDTOS;
    }

}
