package com.example.lmssystem.dtos;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LessonDTO {

    private Long id;
    private String name;
    private String description;
    private String content;
    private Integer lessonOrder;

}
