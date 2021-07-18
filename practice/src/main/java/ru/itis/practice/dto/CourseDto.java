package ru.itis.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.practice.models.Course;
import ru.itis.practice.models.Pupil;
import ru.itis.practice.models.Teacher;
import ru.itis.practice.models.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    private String title;
    private List<String> teachers;
    private List<String> themes;
    private List<String> pupils;

    public static CourseDto from(Course course) {
        CourseDto result = CourseDto.builder()
                .title(course.getTitle())
                .build();
        if (course.getTeachers() != null) {
            result.setTeachers((course.getTeachers().stream().map(Teacher::getFullText).collect(Collectors.toList())));
        }
        if (course.getThemes() != null) {
            result.setThemes((course.getThemes().stream().map(Theme::getTheme).collect(Collectors.toList())));
        }
        if (course.getPupils() != null) {
            result.setPupils((course.getPupils().stream().map(Pupil::getFullName).collect(Collectors.toList())));
        }
        return result;
    }
}
