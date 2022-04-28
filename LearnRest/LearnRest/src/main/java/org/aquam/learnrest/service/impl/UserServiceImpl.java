package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.repository.UserRepository;
import org.aquam.learnrest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    @Override
    public AppUser findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("User with id: " + userId + " not found"));
    }

    @Override
    public List<AppUser> findAll() {
        if (userRepository.findAll().isEmpty())
            throw new EntitiesNotFoundException("There are no users");
        return userRepository.findAll();
    }

    @Override
    public AppUser updateById(Long userId, UserDTO changedUserDTO) {
        AppUser user = findById(userId);
        AppUser changedUser = toUser(changedUserDTO);
        user.setUsername(changedUser.getUsername());
        user.setPassword(changedUser.getPassword());
        user.setName(changedUser.getName());
        user.setEmail(changedUser.getEmail());
        return userRepository.save(user);
    }

    @Override
    public AppUser registerUser(UserDTO userDTO) {
        // AppUser user = (AppUser) loadUserByUsername(userDTO.getUsername());
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new EntityExistsException("User with username: " + userDTO.getUsername() + " already exists");
        AppUser user = toUser(userDTO);
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteById(Long userId) {
        AppUser user = findById(userId);
        userRepository.delete(user);
        return true;
    }

    public AppUser toUser(@Valid UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validationExceptions = validator.validate(userDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        return user;
    }
}

        /*
        if (userDTO.getUserRole() == null
                || userDTO.getUsername() == null || userDTO.getPassword() == null
                || userDTO.getName() == null || userDTO.getEmail() == null)
            throw new NullPointerException("Some fields are null");
         */