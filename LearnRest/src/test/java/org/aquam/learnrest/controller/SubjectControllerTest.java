package org.aquam.learnrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aquam.learnrest.config.WebSecurityConfig;
import org.aquam.learnrest.config.jwt.JwtTokenProvider;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.SubjectServiceImpl;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @WebMvcTest(controllers = SubjectController.class)
@SpringBootTest(classes = SubjectController.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@AutoConfigureMockMvc
class SubjectControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectServiceImpl subjectService;

    @InjectMocks
    private SubjectController subjectController;

    private static final String ADMIN_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjY2Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE2NTIxODI2MTksImV4cCI6MTY1MjE4NjIxOX0.QOmmKdFjg_HzWOWzIqGgsD-NO0n-uarEioPJm-WDQoU";
    private static final String TEACHER_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbSIsInJvbGUiOiJST0xFX1RFQUNIRVIiLCJpYXQiOjE2NTIxODIzNTcsImV4cCI6MTY1MjE4NTk1N30.S_WtzuhkSiQStnbPWBCuFzTNsaTENxtBrgsseU0Erio";
    private static final String STUDENT_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbWFyYSIsInJvbGUiOiJST0xFX1NUVURFTlQiLCJpYXQiOjE2NTIxODIzNzYsImV4cCI6MTY1MjE4NTk3Nn0.WBOuXDRworqzQXTk3sw_vL0H7P-hfacKKWAYsRLKii4";
    private static final String INVALID_TOKEN = "Bearer " + "not an actual token";

    @Test
    @DisplayName("getAllSubjects")
    void getAllSubjects_RoleAdmin() throws Exception {
        SubjectDTO subject = new SubjectDTO(1L, "subject_name");
        List<SubjectDTO> subjects = Arrays.asList(subject);
        given(subjectService.findAll()).willReturn(subjects);
        ResultActions resultActions = mockMvc.perform(
                                                        get("/learn/subjects")
                                                        .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                     .andExpect(MockMvcResultMatchers.jsonPath("$[0].subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getAllSubjects")
    void getAllSubjects_RoleTeacher() throws Exception {
        SubjectDTO subject = new SubjectDTO(1L, "subject_name");
        List<SubjectDTO> subjects = Arrays.asList(subject);
        given(subjectService.findAll()).willReturn(subjects);
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/subjects")
                                                .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getAllSubjects")
    void getAllSubjects_RoleStudent() throws Exception {
        SubjectDTO subject = new SubjectDTO(1L, "subject_name");
        List<SubjectDTO> subjects = Arrays.asList(subject);
        given(subjectService.findAll()).willReturn(subjects);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/subjects")
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getAllSubjects")
    void getAllSubjectsThrowsEntitiesNotFoundException() throws Exception {
        given(subjectService.findAll()).willThrow(EntitiesNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/subjects")
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("getSubjectById")
    void getSubjectById_RoleAdmin() throws Exception {
        Long subjectId = 1L;
        SubjectDTO subject = new SubjectDTO(subjectId, "subject_name");
        given(subjectService.findById(subjectId)).willReturn(subject);
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/subjects/{subjectId}", subjectId)
                                                .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getSubjectById")
    void getSubjectById_RoleTeacher() throws Exception {
        Long subjectId = 1L;
        SubjectDTO subject = new SubjectDTO(subjectId, "subject_name");
        given(subjectService.findById(subjectId)).willReturn(subject);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/subjects/{subjectId}", subjectId)
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getSubjectById")
    void getSubjectById_RoleStudent() throws Exception {
        Long subjectId = 1L;
        SubjectDTO subject = new SubjectDTO(subjectId, "subject_name");
        given(subjectService.findById(subjectId)).willReturn(subject);
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/subjects/{subjectId}", subjectId)
                                                .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName").value("subject_name"));
    }

    @Test
    @DisplayName("getSubjectById")
    void getSubjectByIdThrowsEntitiesNotFoundException() throws Exception {
        Long subjectId = 1L;
        given(subjectService.findById(subjectId)).willThrow(EntitiesNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/subjects/{subjectId}", subjectId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("createSubject")
    void createSubject_RoleAdmin() throws Exception {
        SubjectDTO subject = new SubjectDTO(null, "subject_name");
        SubjectDTO subject2 = new SubjectDTO(1L, "subject_name");
        given(subjectService.create(subject)).willReturn(subject2);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/subjects")
                                                .header("Authorization", ADMIN_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(subject)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName").value("subject_name"));
    }

    // 403 Forbidden
    @Test
    @DisplayName("createSubject")
    void createSubject_RoleTeacher() throws Exception {
        SubjectDTO subject = new SubjectDTO(null, "subject_name");
        SubjectDTO subject2 = new SubjectDTO(1L, "subject_name");
        given(subjectService.create(subject)).willReturn(subject2);
        ResultActions resultActions = mockMvc.perform(
                                            post("/learn/subjects")
                                            .header("Authorization", TEACHER_TOKEN)
                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .content(new ObjectMapper().writeValueAsString(subject)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("createSubject")
    void createSubjectThrowsEntityExistsException() throws Exception {
        SubjectDTO subject = new SubjectDTO(null, "subject_name");
        given(subjectService.create(subject)).willThrow(EntityExistsException.class);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/subjects")
                                                .header("Authorization", ADMIN_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(subject)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("addFile")
    void addFile_RoleAdmin() throws Exception {
        Long subjectId = 1L;
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.png", "image/png", "some xml".getBytes());
        Subject subject = new Subject(subjectId, "subject_name", null);
        given(subjectService.addFile(subjectId, firstFile)).willReturn(subject);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                                                            .multipart("/learn/subjects/upload/{subjectId}", subjectId)
                                                            .file(firstFile)
                                                            .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId").value(1));
    }

    @Test
    @DisplayName("addFile")
    void addFile_RoleTeacher() throws Exception {
        Long subjectId = 1L;
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.png", "image/png", "some xml".getBytes());
        Subject subject = new Subject(subjectId, "subject_name", null);
        given(subjectService.addFile(subjectId, firstFile)).willReturn(subject);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                                                        .multipart("/learn/subjects/upload/{subjectId}", subjectId)
                                                        .file(firstFile)
                                                        .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("updateSubject")
    void updateSubject_RoleAdmin() throws Exception {
        Long subjectId = 1L;
        SubjectDTO subject = new SubjectDTO(null, "subject_name");
        SubjectDTO subject2 = new SubjectDTO(subjectId, "subject_name");
        given(subjectService.updateById(subjectId, subject)).willReturn(subject2);
        ResultActions resultActions = mockMvc.perform(
                                                put("/learn/subjects/{subjectId}", subjectId)
                                                .header("Authorization", ADMIN_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(subject)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectId").value(subjectId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName").value("subject_name"));
    }

    // 403 Forbidden
    @Test
    @DisplayName("updateSubject")
    void updateSubject_RoleTeacher() throws Exception {
        Long subjectId = 1L;
        SubjectDTO subject = new SubjectDTO(null, "subject_name");
        ResultActions resultActions = mockMvc.perform(
                                                put("/learn/subjects/{subjectId}", subjectId)
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(subject)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteSubjectById")
    void deleteSubjectById_RoleAdmin() throws Exception {
        Long subjectId = 1L;
        given(subjectService.deleteById(subjectId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/subjects/{subjectId}", subjectId)
                                                    .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }

    @Test
    @DisplayName("deleteSubjectById")
    void deleteSubjectById_RoleStudent() throws Exception {
        Long subjectId = 1L;
        given(subjectService.deleteById(subjectId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/subjects/{subjectId}", subjectId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteSubjectById")
    void deleteSubjectByIdThrows() throws Exception {
        Long subjectId = 1L;
        given(subjectService.deleteById(subjectId)).willThrow(ConstraintViolationException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/subjects/{subjectId}", subjectId)
                                                    .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

}
/*
        FileInputStream fis=new FileInputStream("D:\\iTechArtProject\\LearnRest\\src\\main\\resources\\static\\subject_images\\dog.jpg");
        MockMultipartFile upload = new MockMultipartFile("upload",fis);

        MockMultipartFile A_FILE
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );


        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        MockMultipartFile newSubjectDTO = new MockMultipartFile("newSubjectDTO", null,
                "application/json", "{\"subjectName\": \"subject_name\", \"filePath\": \"file_path\"}".getBytes());

        given(subjectService.create(newSubjectDTO, file)).willReturn(subject);

        ResultActions resultActions = mockMvc.perform(multipart("/learn/subjects")
                        .file(A_FILE)
                        .file(employeeJson))
                .andExpect(status().isOk());
        resultActions.andDo(print());
         */