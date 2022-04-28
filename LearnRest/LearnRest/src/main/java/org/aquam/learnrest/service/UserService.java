package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    List<AppUser> findAll();
    AppUser updateById(Long userId, UserDTO changedUserDTO);
    boolean deleteById(Long userId);
    AppUser registerUser(UserDTO userDTO);

}
