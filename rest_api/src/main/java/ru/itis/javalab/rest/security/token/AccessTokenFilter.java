
package ru.itis.javalab.rest.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.javalab.rest.models.RefreshToken;
import ru.itis.javalab.rest.models.User;
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
public class AccessTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretWord;

    @Value("${jwt.accessExpiresInMs}")
    private Long accessExp;


    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {

        String accessToken = request.getHeader("access-token");
        if (accessToken != null) {
            if (tokenUtil.valid(accessToken)) {
                TokenAuthentication tokenAuthentication = new TokenAuthentication(tokenUtil.getClaim("redisId", accessToken).asString());
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.addHeader("user-id", tokenUtil.decode(accessToken).getSubject());
            }
        }
        filterChain.doFilter(request, response);
    }
}
