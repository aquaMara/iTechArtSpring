package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findByIdBase(Long id);
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
    UserDTO updateById(Long userId, UserDTO changedUserDTO);
    UserDTO updateByUsername(String username, UserDTO changedUserDTO);
    boolean deleteById(Long userId);
    AppUser registerUser(UserDTO userDTO);

}
