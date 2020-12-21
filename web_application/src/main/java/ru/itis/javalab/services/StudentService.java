package ru.itis.javalab.services;

import ru.itis.javalab.models.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
}
