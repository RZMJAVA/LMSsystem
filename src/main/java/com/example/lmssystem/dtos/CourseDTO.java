package com.example.lmssystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private List<ChapterDTO> chapterDTOs;

}
