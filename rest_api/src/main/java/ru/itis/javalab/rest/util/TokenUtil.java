package ru.itis.javalab.rest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.redis.models.RedisUser;
import ru.itis.javalab.rest.redis.repositories.RedisUserRepository;

import java.util.Date;
import java.util.Map;

@Component
public class TokenUtil {

    @Autowired
    private RedisUserRepository redisUserRepository;


    @Value("${token.max-amount}")
    private Integer maxAmount;

    @Value("${jwt.secret}")
    private String secretWord;

    @Value("${jwt.accessExpiresInMs}")
    private Long accessExpiration;

    @Value("${jwt.refreshExpiresInMs}")
    private Long refreshExpiration;

    public String getAccessToken(User user) {

        Date accessExp = new Date();
        accessExp.setTime(System.currentTimeMillis() + accessExpiration);

        String access = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("redisId", user.getRedisId())
                .withClaim("role", String.valueOf(user.getRole()))
                .withExpiresAt(accessExp)
                .sign(Algorithm.HMAC256(secretWord));

        return access;
    }

    public String getRefreshToken(User user) {

        Date refreshExp = new Date();
        refreshExp.setTime(System.currentTimeMillis() + refreshExpiration);

        String refresh = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(refreshExp)
                .sign(Algorithm.HMAC256(secretWord));

        return refresh;
    }

    public RefreshToken getUserRefresh(String token) {

        RedisUser redisUser = redisUserRepository
                .findById(getClaim("redisId", token).asString())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return RefreshToken.builder()
                .refreshToken(redisUser.getTokens().get(0))
                .build();
    }

    public boolean valid(String token) {

        Long currentDate = new Date().getTime();
        Long expires = decode(token).getExpiresAt().getTime();

        return currentDate < expires;
    }

    public DecodedJWT decode(String token) {

        return JWT.require(Algorithm.HMAC256(secretWord))
                .build()
                .verify(token);
    }

    public Claim getClaim(String claim, String token) {

        Map<String, Claim> claims = decode(token).getClaims();

        return claims.get(claim);
    }
}
