package org.aquam.learnrest.config.jwt;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.exception.handler.AppExceptionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

// фильтрует запросы на наличие токена
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    // private final AppExceptionHandler appExceptionHandler;


    // token from request
    // jwtTokenProvider.validateToken(token) - токен валиден
    // каждый запрос проверяем на токен
    // есть токен = можно продолжатьб нет токена = нет и аутентификации, до свидания
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        StringBuffer requestURL = ((HttpServletRequest) servletRequest).getRequestURL();
        Enumeration<String> h2 = ((HttpServletRequest) servletRequest).getHeaderNames();
        Collections.list(h2).forEach(System.out::println);

        if (((HttpServletRequest) servletRequest).getServletPath().equals("/learn/login")
            || ((HttpServletRequest) servletRequest).getServletPath().equals("/learn/register")
            || ((HttpServletRequest) servletRequest).getRequestURI().equals("/learn/login")
            || ((HttpServletRequest) servletRequest).getRequestURI().equals("/learn/register")) {

            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            boolean isValid = jwtTokenProvider.validateToken(token);
            if (!isValid)
                throw new JwtException("Invalid token");
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }
}
