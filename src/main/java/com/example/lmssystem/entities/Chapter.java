package com.example.lmssystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_chapters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "chapter_order")
    private Integer chapterOrder;

    @ManyToOne
    @JoinColumn(name = "course")
    private Course course;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

}
