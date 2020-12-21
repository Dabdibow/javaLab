package ru.itis.javalab.repositories;

import ru.itis.javalab.models.Student;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student> {
    List<Student> findAll();
}
