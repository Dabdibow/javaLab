package ru.itis.javalab.rest.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.rest.dto.AccessTokenDto;
import ru.itis.javalab.rest.dto.RefreshTokenDto;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.redis.services.RedisUserService;
import ru.itis.javalab.rest.repositories.RefreshTokenRepository;
import ru.itis.javalab.rest.repositories.UsersRepository;
import ru.itis.javalab.rest.dto.EmailPasswordDto;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.util.TokenUtil;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessExpiresInMs}")
    private String accessExpInMs;

    @Value("${jwt.refreshExpiresInMs}")
    private String refreshExpInMs;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUserService redisUserService;

    @Override
    public AccessTokenDto login(EmailPasswordDto emailPassword) {

        User user;
        try {
            user = usersRepository.findByEmail(emailPassword.getEmail()).orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }

        if (passwordEncoder.matches(emailPassword.getPassword(), user.getHashPassword())) {

            RefreshToken token = RefreshToken.builder()
                    .refreshToken(tokenUtil.getRefreshToken(user))
                    .user(user)
                    .build();

//            tokensRepository.save(token);
            redisUserService.addTokenToUser(user, token.getRefreshToken());

            return AccessTokenDto.builder().accessToken(tokenUtil.getAccessToken(user)).build();
        } else {
            throw new UsernameNotFoundException("Invalid name or password");
        }
    }

    @Override
    public AccessTokenDto createAccessTokenDto(User user) {
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withClaim("state", user.getState().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(accessExpInMs)))
                .sign(Algorithm.HMAC256(secret));

        return AccessTokenDto.builder().accessToken(accessToken).build();
    }

    @Override
    public RefreshTokenDto createRefreshTokenDto(User user) {
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(refreshExpInMs)))
                .sign(Algorithm.HMAC256(secret));

        return RefreshTokenDto.builder().refreshToken(accessToken).build();
    }
}
