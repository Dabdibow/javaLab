package ru.itis.javalab.services;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public boolean containsUserAuth(String uuid) {
        return !getUsersByUUID(uuid).isEmpty();
    }

    @Override
    public List<User> getUsersByUUID(String uuid) {
        return this.usersRepository.findAllByUUID(uuid);
    }

    @Override
    public String getUUIDByCredentials(String username, String password) {
        List<User> users = this.usersRepository.findByCredentials(username, password);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0).getUUID();
    }

    @Override
    public void addUser(User user) {
        this.usersRepository.save(user);
    }

    @Override
    public String getPasswordByUsername(String username) {
        return this.usersRepository.getPasswordByUsername(username).get(0).getPassword();
    }

    @Override
    public User getUserByUserName(String username) {
        if (!this.usersRepository.getUserByUsername(username).isEmpty()) {
            return this.usersRepository.getUserByUsername(username).get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean containsUser(String username, String hashPassword) {
        return this.usersRepository.containsUser(username, hashPassword);
    }

}
