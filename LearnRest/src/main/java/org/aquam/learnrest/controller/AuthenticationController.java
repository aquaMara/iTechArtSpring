package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.config.jwt.JwtTokenProvider;
import org.aquam.learnrest.config.jwt.UserAuthenticationDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/learn/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;

    @PostMapping("")
    public ResponseEntity login(@RequestBody UserAuthenticationDTO userAuthenticationDTO) {
        try {
            String username = userAuthenticationDTO.getUsername();
            String password = userAuthenticationDTO.getPassword();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);
            AppUser user = (AppUser) userService.loadUserByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("Such user does not exist");

            String token = jwtTokenProvider.createToken(username, user.getUserRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("role", user.getUserRole());
            return ResponseEntity.ok(response);

        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}
