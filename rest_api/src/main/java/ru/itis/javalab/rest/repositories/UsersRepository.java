package ru.itis.javalab.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.rest.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
