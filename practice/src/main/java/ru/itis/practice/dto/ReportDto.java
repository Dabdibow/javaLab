package ru.itis.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.practice.models.Pupil;
import ru.itis.practice.models.Report;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReportDto {

    private Long id;
    private String course;
    private String pupil;
    private String teacher;

    public static ReportDto from (Report report) {
        ReportDto result = ReportDto.builder()
                .id(report.getId())
                .course(report.getCourse())
                .pupil(report.getPupil())
                .teacher(report.getTeacher())
                .build();

        return result;
    }
}
