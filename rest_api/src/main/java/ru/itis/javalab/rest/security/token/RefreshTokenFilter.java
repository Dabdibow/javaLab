
package ru.itis.javalab.rest.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;
import ru.itis.javalab.rest.redis.services.RedisUserService;
import ru.itis.javalab.rest.repositories.RefreshTokenRepository;
import ru.itis.javalab.rest.repositories.UsersRepository;
import ru.itis.javalab.rest.util.TokenUtil;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.function.Supplier;

@Component
public class RefreshTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretWord;

    @Value("${jwt.accessExpiresInMs}")
    private Long accessExp;

    private final RequestMatcher refreshRequest = new AntPathRequestMatcher("/refresh", "POST");

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUserService redisUserService;


    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {

        if (refreshRequest.matches(request)) {
            String access = request.getHeader("access-token");
            if (access != null) {
                RefreshToken refresh = redisUserService.getUserRefresh(access);
                request.setAttribute("refresh-status", tokenUtil.valid(refresh.getRefreshToken()));
            }
        }
        filterChain.doFilter(request, response);
    }
}
