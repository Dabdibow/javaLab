package ru.itis.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.practice.models.Course;
import ru.itis.practice.models.Pupil;
import ru.itis.practice.models.Teacher;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PupilDto {

    private Long id;
    private String firstName;
    private String lastName;

    public static PupilDto from(Pupil pupil) {
        PupilDto result = PupilDto.builder()
                .id(pupil.getId())
                .firstName(pupil.getFirstName())
                .lastName(pupil.getLastName())
                .build();
        return result;
    }

}
