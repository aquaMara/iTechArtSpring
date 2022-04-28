package org.aquam.learnrest.dto.mapper;

import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.AppUser;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class UserMapper {

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }
    // все поля меняем при update
    public AppUser toUser(@Valid UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validationExceptions = validator.validate(userDTO);
        if (!validationExceptions.isEmpty()) {
            throw new ConstraintViolationException(validationExceptions);
        }
        if (userDTO.getUserRole() == null
            || userDTO.getUsername() == null || userDTO.getPassword() == null
            || userDTO.getName() == null || userDTO.getEmail() == null)
            throw new NullPointerException("Some fields are null");
        if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank()
            || userDTO.getName().isBlank() || userDTO.getEmail().isBlank())
            throw new EmptyInputException("Some fields are empty");
        AppUser user = new AppUser();
        user.setUserRole(userDTO.getUserRole());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    /*
    public Subject mapToSubject(SubjectDTO subjectDTO) {
        if (subjectDTO.getSubjectName() == null)
            throw new NullPointerException("Subject is null");
        if (subjectDTO.getSubjectName().isBlank())
            throw new EmptyInputException("Subject name is blank");
        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        return subject;
    }
     */
}
