package org.aquam.learnrest.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.exception.JwtAuthenticationException;
import org.aquam.learnrest.exception.handler.AppExceptionHandler;
import org.aquam.learnrest.model.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    // сколько действует токен
    @Value("${jwt.token.expired}")
    private Long validityInMilliseconds;

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppExceptionHandler appExceptionHandler;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // public String createToken(String username, List<UserRole> roles) {
    public String createToken(String username, UserRole role) {
        // Claims - набор инфы, которой обмениваются, то есть она в токене
        Claims claims = Jwts.claims().setSubject(username);
        // claims.put("roles", getRoleNames(roles));
        claims.put("role", role.name());

        Date currentMoment = new Date();
        Date validityLength = new Date(currentMoment.getTime() + validityInMilliseconds);

        // генерация токена
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentMoment)
                .setExpiration(validityLength)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7, bearerToken.length());

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date()))
                return false;
            return true;
        } catch (JwtException exception) {
            // throw new JwtAuthenticationException("");
            // appExceptionHandler.handleRuntimeException(exception);
        } catch (IllegalArgumentException exception) {
            // appExceptionHandler.handleRuntimeException(exception);
            // throw new IllegalStateException("");
        }
        return false;
        /*
        catch (JwtException | IllegalArgumentException exception) {
            //} catch (JwtException Exception exception) {
            // exception.printStackTrace();
            appExceptionHandler.handleJwtException((JwtException) exception);
            // throw new JwtAuthenticationException("JWT token is expired or invalid");
            // throw new AuthenticationException("JWT token is invalid");
            // throw new AccessDeniedException("DENIED");
        }
         */
    }

    private List<String> getRoleNames(List<UserRole> roles) {
        List<String> result = new ArrayList<>();
        roles.forEach(role -> result.add(role.name()));
        return result;
    }
}
