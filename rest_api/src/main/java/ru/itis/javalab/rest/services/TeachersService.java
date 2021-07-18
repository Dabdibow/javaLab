package ru.itis.javalab.rest.services;

import ru.itis.javalab.rest.dto.TeacherDto;

import java.util.List;

public interface TeachersService {
    List<TeacherDto> getAllTeachers();

    TeacherDto addTeacher(TeacherDto teacher);

    TeacherDto updateTeacher(Long teacherId, TeacherDto teacher);

    void deleteTeacher(Long teacherId);
}
