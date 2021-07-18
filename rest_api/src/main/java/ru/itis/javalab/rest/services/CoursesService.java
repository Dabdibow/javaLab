package ru.itis.javalab.rest.services;

import ru.itis.javalab.rest.dto.CourseDto;
import ru.itis.javalab.rest.dto.TeacherDto;
import ru.itis.javalab.rest.models.Course;

import java.util.List;

public interface CoursesService {
    List<Course> getAllCourses();

    Course addCourse(CourseDto course);

    Course addTeacherIntoCourse(Long courseId, TeacherDto teacher);
}
