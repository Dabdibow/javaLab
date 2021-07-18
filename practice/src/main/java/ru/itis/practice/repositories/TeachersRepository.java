package ru.itis.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.practice.models.Teacher;

import java.util.List;


public interface TeachersRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByIsDeletedIsNull();
}
