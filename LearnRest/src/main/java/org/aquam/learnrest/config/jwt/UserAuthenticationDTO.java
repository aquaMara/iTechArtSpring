package org.aquam.learnrest.config.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuthenticationDTO {

    private String username;
    private String password;
}
