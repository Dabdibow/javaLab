package ru.itis.practice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Pupil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hashPassword;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "pupils")
    private List<Course> courses;

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

}
