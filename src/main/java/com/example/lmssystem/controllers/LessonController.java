package com.example.lmssystem.controllers;

import com.example.lmssystem.dtos.LessonDTO;
import com.example.lmssystem.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters/{chapterId}/lessons")
@RequiredArgsConstructor

public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<LessonDTO> getLessons(@PathVariable Long chapterId) {
        return lessonService.getByChapterId(chapterId);
    }

    @GetMapping("/{id}")
    public LessonDTO getLesson(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public LessonDTO createLesson(@PathVariable Long chapterId, @RequestBody LessonDTO lessonDTO) {
        return lessonService.createLesson(lessonDTO, chapterId);
    }

    @PutMapping("/{id}")
    public LessonDTO updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        return lessonService.updateLesson(id, lessonDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
    }

}
