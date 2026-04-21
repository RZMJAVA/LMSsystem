package com.example.lmssystem.repositories;

import com.example.lmssystem.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Optional<Chapter> findById(Long id);

    List<Chapter> findAllByCourseId(Long courseId);

}
