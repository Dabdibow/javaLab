package ru.itis.practice.controllers;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.practice.dto.CourseDto;
import ru.itis.practice.dto.PupilDto;
import ru.itis.practice.dto.TeacherDto;
import ru.itis.practice.services.CoursesService;
import ru.itis.practice.services.CoursesServiceImpl;

import java.io.FileNotFoundException;

@RestController
public class CoursesController {
    @Autowired
    private CoursesServiceImpl service;

    @GetMapping("/report/{course}/{pupil}/{teacher}/{format}")
    public String generateReport(@PathVariable String course,@PathVariable String pupil,@PathVariable String teacher,@PathVariable String format) throws FileNotFoundException, JRException{
        return service.getReportInfo( course, pupil, teacher,format);
    }


}
