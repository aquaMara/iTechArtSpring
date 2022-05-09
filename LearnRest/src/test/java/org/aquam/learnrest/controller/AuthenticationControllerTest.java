package org.aquam.learnrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aquam.learnrest.config.WebSecurityConfig;
import org.aquam.learnrest.config.jwt.JwtTokenProvider;
import org.aquam.learnrest.config.jwt.UserAuthenticationDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.UserRole;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthenticationController.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    /*

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserServiceImpl userService;
    @InjectMocks
    private AuthenticationController authenticationController;

    private static final String TOKEN = "This is a token";

    @Test
    @DisplayName("login")
    void login() throws Exception {
        String username = "username";
        String password = "password";
        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO(username, password);
        AppUser userDetails = new AppUser(1L, UserRole.ROLE_TEACHER, "username1", "password1", "name1", "email1@gmail.com");
        given(userService.loadUserByUsername(username)).willReturn(userDetails);

        given(jwtTokenProvider.createToken(username, userDetails.getUserRole())).willReturn(TOKEN);

        ResultActions resultActions = mockMvc.perform(
                                                    post("/learn/login")
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(new ObjectMapper().writeValueAsString(userAuthenticationDTO)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());

    }
    @Test
    @DisplayName("login")
    void loginThrowsBadCredentialsException() throws Exception {
        String username = "username";
        String password = "password";
        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO(username, password);
        given(userService.loadUserByUsername(username)).willReturn(null);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/login")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(userAuthenticationDTO)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid username or password"));

    }

     */
}