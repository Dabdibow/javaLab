package ru.itis.javalab.rest.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.javalab.rest.dto.AccessTokenDto;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.repositories.RefreshTokenRepository;
import ru.itis.javalab.rest.repositories.UsersRepository;
import ru.itis.javalab.rest.security.details.UserDetailsImpl;
import ru.itis.javalab.rest.util.TokenUtil;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class RefreshServiceImpl implements RefreshService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LoginService loginService;


    @SneakyThrows
    @Override
    public AccessTokenDto refresh(AccessTokenDto token) {
        DecodedJWT aToken = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.getAccessToken());

        Long userId = Long.parseLong(aToken.getSubject());

        User user = usersRepository.findById(userId)
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        return loginService.createAccessTokenDto(user);
    }

    @Override
    public boolean isValid(String token) {
        Date date = JWT.decode(token).getExpiresAt();
        return date.before(new Date());
    }
}

