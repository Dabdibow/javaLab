package ru.itis.javalab.rest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import ru.itis.javalab.rest.security.token.AccessTokenFilter;
import ru.itis.javalab.rest.security.token.RefreshTokenFilter;
import ru.itis.javalab.rest.security.token.TokenAuthenticationProvider;
import ru.itis.javalab.rest.security.token.TokenLogoutFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessTokenFilter accessTokenFilter;

    @Autowired
    private RefreshTokenFilter refreshTokenFilter;

    @Autowired
    private TokenLogoutFilter tokenLogoutFilter;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().disable();
        http
                .addFilterAt(tokenLogoutFilter, LogoutFilter.class)
                .addFilterBefore(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(accessTokenFilter, RefreshTokenFilter.class)
                .authorizeRequests()
                .antMatchers("/teachers").hasAuthority("ADMIN")
                .antMatchers("/login").permitAll()
                .and()
                .sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
