package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.UserRole;
import org.aquam.learnrest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    /*
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("loadUserByUsername")
    void loadUserByUsernameShouldReturnUser() {
        String username = "username";
        AppUser user = new AppUser(1L, UserRole.ROLE_TEACHER, username, "password", "name", "email@gmail.com");
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        userService.loadUserByUsername(username);
        then(userRepository).should().findByUsername(username);
    }

    @Test
    @DisplayName("loadUserByUsername")
    void loadUserByUsernameShouldThrowUsernameNotFoundException() {
        String username = "username";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(username));
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldReturnUser() {
        Long userId = 1L;
        AppUser user = new AppUser(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        userService.findById(userId);
        then(userRepository).should().findById(userId);
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldThrowEntityNotFoundException() {
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> userService.findById(userId));
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfUsers() {
        AppUser user = new AppUser(1L, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        given(userRepository.findAll())
                .willReturn(Arrays.asList(user));
        userService.findAll();
        then(userRepository).should(times(2)).findAll();
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldThrowEntitiesNotFoundException() {
        given(userRepository.findAll()).willReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> userService.findAll());
    }

    @Test
    @DisplayName("updateById")
    void updateByIdShouldReturnUser() {
        Long userId = 1L;
        AppUser user = new AppUser(userId, UserRole.ROLE_TEACHER, "username1", "password1", "name1", "email1@gmail.com");
        UserDTO changedUserDTO = new UserDTO(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        AppUser changedUser = new AppUser(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        // given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(modelMapper.map(changedUserDTO, AppUser.class)).willReturn(changedUser);
        userService.updateById(userId, changedUserDTO);
        then(userRepository).should().save(user);
    }

    @Test
    @DisplayName("registerUser")
    void registerUserShouldReturnUser() {
        String username = "username";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        UserDTO userDTO = new UserDTO(1L, UserRole.ROLE_TEACHER, username, "password", "name", "email@gmail.com");
        AppUser user = new AppUser(1L, UserRole.ROLE_TEACHER, username, "password", "name", "email@gmail.com");
        given(modelMapper.map(userDTO, AppUser.class)).willReturn(user);
        given(bCryptPasswordEncoder.encode(user.getPassword())).willReturn("encoded_password");
        userService.registerUser(userDTO);
        then(userRepository).should().save(user);
    }

    @Test
    @DisplayName("registerUser")
    void registerUserShouldThrowEntityExistsException() {
        String username = "username";
        AppUser user = new AppUser(1L, UserRole.ROLE_TEACHER, username, "password", "name", "email@gmail.com");
        UserDTO userDTO = new UserDTO(1L, UserRole.ROLE_TEACHER, username, "password", "name", "email@gmail.com");
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        assertThrows(EntityExistsException.class,
                () -> userService.registerUser(userDTO));
    }

    @Test
    @DisplayName("deleteById")
    void deleteByIdShouldDeleteUser() {
        Long userId = 1L;
        AppUser user = new AppUser(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        userService.deleteById(userId);
        then(userRepository).should().delete(user);
    }

    @Test
    @DisplayName("deleteById")
    void deleteByIdShouldThrow() {
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteById(userId));
    }

    @Test
    void toUser() {
    }

     */
}