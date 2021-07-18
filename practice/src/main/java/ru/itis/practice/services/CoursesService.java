package ru.itis.practice.services;

import net.sf.jasperreports.engine.JRException;
import ru.itis.practice.dto.CourseDto;
import ru.itis.practice.dto.PupilDto;
import ru.itis.practice.dto.TeacherDto;
import ru.itis.practice.models.Course;

import java.io.FileNotFoundException;
import java.util.List;

public interface CoursesService {
    List<Course> getAllCourses();

    Course addCourse(CourseDto course);

    Course addTeacherIntoCourse(Long courseId, TeacherDto teacher);


    String getReportInfo(String course, String  pupil , String  teacher, String format) throws FileNotFoundException, JRException;

}
