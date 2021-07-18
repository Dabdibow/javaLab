package ru.itis.javalab.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.rest.dto.AccessTokenDto;
import ru.itis.javalab.rest.redis.services.RedisUserService;

import javax.servlet.http.HttpServletRequest;


@RestController
public class RefreshController {

    @Autowired
    private RedisUserService redisUserService;


    @PostMapping("/refresh/{user-id}")
    public ResponseEntity<AccessTokenDto> updateTokens(@PathVariable("user-id") Long id, HttpServletRequest request) {

        Boolean refreshIsValid = (Boolean) request.getAttribute("refresh-status");

        if (refreshIsValid) {
            String access = redisUserService.updateUserTokens(id);

            return ResponseEntity.ok(AccessTokenDto.builder()
                    .accessToken(access)
                    .build());
        }

        return ResponseEntity.status(401).build();
    }
}