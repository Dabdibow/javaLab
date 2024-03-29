package ru.itis.javalab.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.rest.models.Teacher;

import java.util.List;

public interface TeachersRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByIsDeletedIsNull();
}
