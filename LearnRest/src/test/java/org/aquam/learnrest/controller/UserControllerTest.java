package org.aquam.learnrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aquam.learnrest.config.WebSecurityConfig;
import org.aquam.learnrest.config.jwt.JwtTokenProvider;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.UserRole;
import org.aquam.learnrest.service.impl.SectionServiceImpl;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private UserController userController;

    private static final String ADMIN_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjY2Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE2NTIxODI2MTksImV4cCI6MTY1MjE4NjIxOX0.QOmmKdFjg_HzWOWzIqGgsD-NO0n-uarEioPJm-WDQoU";
    private static final String TEACHER_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbSIsInJvbGUiOiJST0xFX1RFQUNIRVIiLCJpYXQiOjE2NTIxODIzNTcsImV4cCI6MTY1MjE4NTk1N30.S_WtzuhkSiQStnbPWBCuFzTNsaTENxtBrgsseU0Erio";
    private static final String STUDENT_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbWFyYSIsInJvbGUiOiJST0xFX1NUVURFTlQiLCJpYXQiOjE2NTIxODIzNzYsImV4cCI6MTY1MjE4NTk3Nn0.WBOuXDRworqzQXTk3sw_vL0H7P-hfacKKWAYsRLKii4";
    private static final String INVALID_TOKEN = "Bearer " + "not an actual token";


    @BeforeEach
    void setUp() {
        UserDetails userDetails = new AppUser(1L, UserRole.ROLE_TEACHER, "username1", "password1", "name1", "email1@gmail.com");
        String bearerToken = TEACHER_TOKEN;
        String token = bearerToken.substring(7, bearerToken.length());
        given(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).willReturn(token);
        given(jwtTokenProvider.getAuthentication(token)).willReturn(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        given(jwtTokenProvider.validateToken(token)).willReturn(true);
    }

    @Test
    @DisplayName("getAllUsers")
    void getAllUsers_RoleAdmin() throws Exception {

        UserDTO user1 = new UserDTO(1L, UserRole.ROLE_STUDENT, "username1", "password1", "name1", "email1@gmail.com");
        UserDTO user2 = new UserDTO(2L, UserRole.ROLE_TEACHER, "username2", "password2", "name2", "email2@gmail.com");
        given(userService.findAll()).willReturn(Arrays.asList(user1, user2));
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/users")
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("username1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value("password2"));
    }

    @Test
    @DisplayName("getUserAccount")
    void getUserAccount() throws Exception {
        Long userId = 1L;
        UserDTO user = new UserDTO(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        given(userService.findById(userId)).willReturn(user);
        ResultActions resultActions = mockMvc.perform(
                                            get("/learn/users/{userId}", userId)
                                            .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"));
    }

    @Test
    @DisplayName("updateUser")
    void updateUser() throws Exception {
        Long userId = 1L;
        UserDTO user = new UserDTO(null, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        UserDTO user2 = new UserDTO(userId, UserRole.ROLE_TEACHER, "username", "password", "name", "email@gmail.com");
        given(userService.updateById(userId, user)).willReturn(user2);
        ResultActions resultActions = mockMvc.perform(
                                                put("/learn/users/{userId}", userId)
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(user)));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"));
    }

    @Test
    @DisplayName("deleteUserById")
    void deleteUserById() throws Exception {
        Long userId = 1L;
        given(userService.deleteById(userId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                delete("/learn/users/{userId}", userId)
                                                .header("Authorization", TEACHER_TOKEN));
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }

}