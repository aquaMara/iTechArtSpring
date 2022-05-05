package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/learn/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserServiceImpl userService;

    @PostMapping(path = "")
    public ResponseEntity<AppUser> createUser(@RequestBody UserDTO userDTO) throws IOException {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }


}
