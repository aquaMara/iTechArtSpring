package org.aquam.learnrest.config.jwt;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.exception.handler.AppExceptionHandler;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    // private final AppExceptionHandler appExceptionHandler;

    // каждый запрос перед тем как быть переданным серверу проходит через проверку JwtTokenFilter
    // JwtTokenFilter проверяет токен на валидность
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(filterChainExceptionHandler, JwtTokenFilter.class);
    }
}

