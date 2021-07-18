package ru.itis.javalab.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.rest.models.Course;


public interface CoursesRepository extends JpaRepository<Course, Long> {
}
