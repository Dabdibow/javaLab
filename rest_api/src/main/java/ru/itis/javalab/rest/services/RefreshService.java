package ru.itis.javalab.rest.services;

import ru.itis.javalab.rest.dto.AccessTokenDto;

public interface RefreshService {
    AccessTokenDto refresh(AccessTokenDto requestToken);
    boolean isValid(String requestToken);
}
