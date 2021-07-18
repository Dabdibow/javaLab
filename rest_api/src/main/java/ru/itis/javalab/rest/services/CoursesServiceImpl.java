package ru.itis.javalab.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.rest.repositories.CoursesRepository;
import ru.itis.javalab.rest.repositories.TeachersRepository;
import ru.itis.javalab.rest.dto.CourseDto;
import ru.itis.javalab.rest.dto.TeacherDto;
import ru.itis.javalab.rest.models.Course;
import ru.itis.javalab.rest.models.Teacher;

import java.util.List;


@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @Override
    public List<Course> getAllCourses() {
        return coursesRepository.findAll();
    }

    @Override
    public Course addCourse(CourseDto course) {
        return coursesRepository.save(Course.builder()
                .title(course.getTitle())
                .build());
    }

    @Override
    public Course addTeacherIntoCourse(Long courseId, TeacherDto teacher) {
        Course course = coursesRepository.findById(courseId)
                .orElseThrow(IllegalArgumentException::new);
        Teacher teacherForCourse = teachersRepository.findById(teacher.getId())
                .orElseThrow(IllegalArgumentException::new);

        course.getTeachers().add(teacherForCourse);
        coursesRepository.save(course);
        return course;
    }
}
