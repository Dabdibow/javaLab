package ru.itis.javalab.rest.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.redis.models.RedisUser;
import ru.itis.javalab.rest.redis.repositories.RedisUserRepository;
import ru.itis.javalab.rest.repositories.UsersRepository;
import ru.itis.javalab.rest.util.TokenUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Autowired
    private RedisUserRepository redisUsersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public void addTokenToUser(User user, String token) {

        String redisId = user.getRedisId();
        RedisUser redisUser;

        if (redisId != null) { //redis token exists
            redisUser = redisUsersRepository.findById(redisId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<String> tokens = redisUser.getTokens();
            if (tokens == null) {
                redisUser.setTokens(new LinkedList<>());
            }
            if (tokens.size() == 0) {
                redisUser.getTokens().add(token);
            } else {
                tokens.remove(tokens.get(0));
                tokens.add(token);
            }
        } else { //no redis token
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(Collections.singletonList(token))
                    .build();
        }

        redisUsersRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        usersRepository.save(user);
    }

    @Override
    public void deleteToken(User user, String token) {

        RedisUser redisUser = redisUsersRepository.findById(user.getRedisId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        redisUser.getTokens().remove(token);
        redisUsersRepository.save(redisUser);
    }

    @Override
    public String updateUserTokens(Long userId) {

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String access = tokenUtil.getAccessToken(user);
        String refresh = tokenUtil.getRefreshToken(user);
        addTokenToUser(user, refresh);

        return access;
    }

    public RefreshToken getUserRefresh(String token) {

        RedisUser redisUser = redisUsersRepository
                .findById(tokenUtil.getClaim("redisId", token).asString())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return RefreshToken.builder()
                .refreshToken(redisUser.getTokens().get(0))
                .build();
    }
}