package ru.itis.javalab.rest.services;

import ru.itis.javalab.rest.dto.AccessTokenDto;
import ru.itis.javalab.rest.dto.EmailPasswordDto;
import ru.itis.javalab.rest.dto.RefreshTokenDto;
import ru.itis.javalab.rest.models.User;


public interface LoginService {
    AccessTokenDto login(EmailPasswordDto emailPassword);
    AccessTokenDto createAccessTokenDto(User user);
    RefreshTokenDto createRefreshTokenDto(User user);
}
