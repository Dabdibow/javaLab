package ru.itis.javalab.rest.security.details;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.redis.models.RedisUser;
import ru.itis.javalab.rest.redis.repositories.RedisUserRepository;
import ru.itis.javalab.rest.repositories.RefreshTokenRepository;
import ru.itis.javalab.rest.repositories.UsersRepository;

import java.util.List;
import java.util.function.Supplier;

@Component("tokenUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisUserRepository redisUserRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String redisId) throws UsernameNotFoundException {

        User user;
        try {
            RedisUser redisUser = redisUserRepository.findById(redisId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<String> tokens = redisUser.getTokens();
            if (tokens != null && tokens.size() > 0) {
                user = usersRepository.findById(redisUser.getUserId())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                return new UserDetailsImpl(user);

            }
        } catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }
        return new UserDetailsImpl(new User());
    }
}
