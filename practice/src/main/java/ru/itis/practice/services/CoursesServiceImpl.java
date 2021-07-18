package ru.itis.practice.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.itis.practice.dto.CourseDto;
import ru.itis.practice.dto.PupilDto;
import ru.itis.practice.dto.TeacherDto;
import ru.itis.practice.models.Course;
import ru.itis.practice.models.Report;
import ru.itis.practice.models.Teacher;
import ru.itis.practice.repositories.CoursesRepository;
import ru.itis.practice.repositories.TeachersRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getReportInfo(String course, String pupil, String teacher,String reportFormat) throws FileNotFoundException, JRException {

        Report report = Report.builder()
                .course(course)
                .pupil(pupil)
                .teacher(teacher)
                .build();

        String path = "C:\\opt\\company\\uploads\\" + course + "_" + pupil;
        /*int n = course.getThemes().size();
        ArrayList<String> list  = new ArrayList<>(n + 3);
        list.add(course.getTitle());
        String Pname = pupil.getFirstName() + " " + pupil.getLastName();
        list.add(Pname);
        String Tname = teacher.getFirstName() + " " + teacher.getLastName();
        list.add(Tname);
        list.addAll(course.getThemes());

        ArrayList<String> list  = new ArrayList<>(3);
        list.add(course);
        list.add(pupil);
        list.add(teacher);*/

        ArrayList<Report> list = new ArrayList<>();
        list.add(report);

        File file = ResourceUtils.getFile("classpath:report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Anton Davydov");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path + ".html");
        }
        if(reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,path + ".pdf");
        }

        return "report generated in path : " + path;
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

