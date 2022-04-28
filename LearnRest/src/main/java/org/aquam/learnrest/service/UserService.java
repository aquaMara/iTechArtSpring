package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    UserDTO findByIdDTO(Long id);
    List<AppUser> findAll();
    List<UserDTO> findAllDTO();
    AppUser updateById(Long userId, UserDTO changedUserDTO);
    UserDTO updateByIdDTO(Long userId, UserDTO changedUserDTO);
    boolean deleteById(Long userId);
    AppUser registerUser(UserDTO userDTO);

}
