package org.aquam.learnrest.dto;

import lombok.*;
import org.aquam.learnrest.model.UserRole;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    // @NotBlank(message = "Can not be empty")
    private UserRole userRole;
    @NotBlank(message = "Can not be empty")  // not null + no whitespaces
    private String username;
    @NotBlank(message = "Can not be empty")
    @Length(min = 6, max = 16, message = "Between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Can not be empty")
    private String name;
    @NotBlank(message = "Can not be empty")
    @Email(message = "Valid only")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return userRole == userDTO.userRole && Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password) && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRole, username, password, name, email);
    }
}
