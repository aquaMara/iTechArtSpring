package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learn/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        System.out.println("Hello");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@AuthenticationPrincipal @PathVariable("username") String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO changedUserDTO) {
        return new ResponseEntity<>(userService.updateById(userId, changedUserDTO), HttpStatus.OK); // 200 for updates, 201 for created
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @PutMapping("/username/{username}")
    public ResponseEntity<UserDTO> updateUserByUsername(@PathVariable String username, @RequestBody UserDTO changedUserDTO) {
        return new ResponseEntity<>(userService.updateByUsername(username, changedUserDTO), HttpStatus.OK); // 200 for updates, 201 for created
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long userId) {
        return new ResponseEntity(userService.deleteById(userId), HttpStatus.OK);
    }
}
/*
    @PostMapping("")
    public ResponseEntity<AppUser> createUser(UserDTO userDTO) throws IOException {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }
 */
