package ru.itis.javalab.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;

import java.util.List;
import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findFirstByUserId(Long id);
    List<RefreshToken> findAllByUser(User user);
}
