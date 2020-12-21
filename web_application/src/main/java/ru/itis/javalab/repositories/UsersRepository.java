package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByUUID(String uuid);
    List<User> findByCredentials(String username, String password);
    List<User> getPasswordByUsername(String username);
    List<User> getUserByUsername(String username);
    boolean containsUser(String username, String hashPassword);
}
