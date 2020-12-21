package ru.itis.javalab.repositories;

import ru.itis.javalab.models.Student;
import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;

public class StudentRepositoryJdbcImpl implements StudentRepository {

    //language=SQL
    private static final String SQL_FIND_ALL_STUDENTS = "select * from student";

    private DataSource dataSource;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    private RowMapper<Student> studentRowMapper = row -> Student.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .groupNumber(row.getInt("group_number"))
            .build();

    public StudentRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public List<Student> findAll() {
        return this.simpleJdbcTemplate.query(SQL_FIND_ALL_STUDENTS, studentRowMapper);
    }

    @Override
    public void save(Student entity) {

    }

    @Override
    public void update(Student entity) {

    }

    @Override
    public void delete(Student entity) {

    }
}
