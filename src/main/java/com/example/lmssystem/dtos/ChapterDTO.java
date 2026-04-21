package com.example.lmssystem.dtos;

import com.example.lmssystem.entities.Course;
import com.example.lmssystem.entities.Lesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChapterDTO {

    private Long id;
    private String name;
    private String description;
    private Integer chapterOrder;
    private List<LessonDTO> lessons;

}
