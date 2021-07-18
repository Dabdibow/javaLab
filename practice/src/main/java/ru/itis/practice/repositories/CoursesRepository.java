package ru.itis.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.practice.models.Course;

import java.util.ArrayList;
import java.util.List;

public interface CoursesRepository extends JpaRepository<Course, Long> {

    //ArrayList<String> getReportInfo();
}
