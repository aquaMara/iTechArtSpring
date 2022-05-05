package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    UserDTO findByIdDTO(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAllDTO();
    UserDTO updateByIdDTO(Long userId, UserDTO changedUserDTO);
    UserDTO updateByUsername(String username, UserDTO changedUserDTO);
    boolean deleteById(Long userId);
    AppUser registerUser(UserDTO userDTO);

}
