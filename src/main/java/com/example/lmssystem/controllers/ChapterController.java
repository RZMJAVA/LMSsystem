package com.example.lmssystem.controllers;

import com.example.lmssystem.dtos.ChapterDTO;
import com.example.lmssystem.services.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/chapters")
@RequiredArgsConstructor

public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping
    public List<ChapterDTO> getChapters(@PathVariable Long courseId) {
        return chapterService.getByCourseId(courseId);
    }

    @GetMapping("/{id}")
    public ChapterDTO getChapter(@PathVariable Long id) {
        return chapterService.getChapterById(id);
    }

    @PostMapping
    public ChapterDTO createChapter(@PathVariable Long courseId, @RequestBody ChapterDTO chapterDTO) {
        return chapterService.createChapter(chapterDTO, courseId);
    }

    @PutMapping("/{id}")
    public ChapterDTO updateChapter(@PathVariable Long id, @RequestBody ChapterDTO chapterDTO) {
        return chapterService.updateChapter(id, chapterDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapterById(id);
    }

}
