package ru.itis.javalab.services;

import ru.itis.javalab.models.Student;
import ru.itis.javalab.repositories.StudentRepository;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }
}
