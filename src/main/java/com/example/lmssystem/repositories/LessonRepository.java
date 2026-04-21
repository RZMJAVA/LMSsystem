package com.example.lmssystem.repositories;

import com.example.lmssystem.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByChapterId(Long chapterId);

}
