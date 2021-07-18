package ru.itis.javalab.rest.redis.services;

import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;

public interface RedisUserService {
    void addTokenToUser(User user, String refreshToken);

    void deleteToken(User user, String refreshToken);

    String updateUserTokens(Long userId);

    RefreshToken getUserRefresh(String refreshToken);
}
