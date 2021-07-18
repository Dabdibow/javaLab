package ru.itis.javalab.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.rest.dto.AccessTokenDto;
import ru.itis.javalab.rest.dto.EmailPasswordDto;
import ru.itis.javalab.rest.services.LoginService;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> login(@RequestBody EmailPasswordDto emailPassword) {
        return ResponseEntity.ok(loginService.login(emailPassword));
    }
}
