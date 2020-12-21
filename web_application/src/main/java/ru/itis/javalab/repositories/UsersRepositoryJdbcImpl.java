package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {


    //language=SQL
    private static final String SQL_FIND_BY_USERNAME = "select * from users where username = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_UUID = "select * from users where uuid = ?";

    //language=SQL
    private static final String SQL_FIND_BY_CREDENTIALS = "select * from users where username = ? and password = ?";

    //language=SQL
    private static final String SQL_ADD_USER = "insert into users (uuid, username, password) values (?, ?, ?)";

    private DataSource dataSource;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .UUID(row.getString("uuid"))
            .userName(row.getString("username"))
            .password(row.getString("password"))
            .build();

    @Override
    public void save(User entity) {
        this.simpleJdbcTemplate.voidQuery(SQL_ADD_USER, entity.getUUID(), entity.getUserName(), entity.getPassword());
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public List<User> findAllByUUID(String uuid) {
        return this.simpleJdbcTemplate.query(SQL_FIND_ALL_BY_UUID, userRowMapper, uuid);
    }

    @Override
    public List<User> findByCredentials(String username, String password) {
        return this.simpleJdbcTemplate.query(SQL_FIND_BY_CREDENTIALS, userRowMapper, username, password);
    }

    @Override
    public List<User> getPasswordByUsername(String username) {
        return this.simpleJdbcTemplate.query(SQL_FIND_BY_USERNAME, userRowMapper, username);
    }

    @Override
    public List<User> getUserByUsername(String username) {
        return this.simpleJdbcTemplate.query(SQL_FIND_BY_USERNAME, userRowMapper, username);
    }

    @Override
    public boolean containsUser(String username, String hashPassword) {
        return !this.simpleJdbcTemplate.query(SQL_FIND_BY_CREDENTIALS, userRowMapper, username, hashPassword).isEmpty();
    }

}