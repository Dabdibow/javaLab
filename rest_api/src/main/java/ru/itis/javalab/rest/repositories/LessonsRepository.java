package ru.itis.javalab.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.rest.models.Lesson;

public interface LessonsRepository extends JpaRepository<Lesson, Long> {
}
