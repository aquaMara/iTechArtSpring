package org.aquam.learnrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aquam.learnrest.config.WebSecurityConfig;
import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.service.impl.SectionServiceImpl;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = SectionController.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@AutoConfigureMockMvc
class SectionControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SectionServiceImpl sectionService;
    @InjectMocks
    private SectionController sectionController;

    private static final String ADMIN_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjY2Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE2NTIxODI2MTksImV4cCI6MTY1MjE4NjIxOX0.QOmmKdFjg_HzWOWzIqGgsD-NO0n-uarEioPJm-WDQoU";
    private static final String TEACHER_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbSIsInJvbGUiOiJST0xFX1RFQUNIRVIiLCJpYXQiOjE2NTIxODIzNTcsImV4cCI6MTY1MjE4NTk1N30.S_WtzuhkSiQStnbPWBCuFzTNsaTENxtBrgsseU0Erio";
    private static final String STUDENT_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbWFyYSIsInJvbGUiOiJST0xFX1NUVURFTlQiLCJpYXQiOjE2NTIxODIzNzYsImV4cCI6MTY1MjE4NTk3Nn0.WBOuXDRworqzQXTk3sw_vL0H7P-hfacKKWAYsRLKii4";
    private static final String INVALID_TOKEN = "Bearer " + "not an actual token";

    @Test
    @DisplayName("getAllSections")
    void getAllSections_RoleAdmin() throws Exception {
        SectionDTO section1 = new SectionDTO(1L, "sectionName1", 1L);
        SectionDTO section2 = new SectionDTO(2L, "sectionName2", 1L);
        given(sectionService.findAll()).willReturn(Arrays.asList(section1, section2));
        ResultActions resultActions = mockMvc.perform(
                                            get("/learn/sections")
                                            .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionName").value("sectionName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionName").value("sectionName2"));
    }

    @Test
    @DisplayName("getAllSections")
    void getAllSections_RoleTeacher() throws Exception {
        SectionDTO section1 = new SectionDTO(1L, "sectionName1", 1L);
        SectionDTO section2 = new SectionDTO(2L, "sectionName2", 1L);
        given(sectionService.findAll()).willReturn(Arrays.asList(section1, section2));
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/sections")
                                                .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionName").value("sectionName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionName").value("sectionName2"));
    }

    @Test
    @DisplayName("getAllSections")
    void getAllSections_RoleStudent() throws Exception {
        SectionDTO section1 = new SectionDTO(1L, "sectionName1", 1L);
        SectionDTO section2 = new SectionDTO(2L, "sectionName2", 1L);
        given(sectionService.findAll()).willReturn(Arrays.asList(section1, section2));
        ResultActions resultActions = mockMvc.perform(
                                        get("/learn/sections")
                                        .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sectionName").value("sectionName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].sectionName").value("sectionName2"));
    }

    @Test
    @DisplayName("getAllSections")
    void getAllSections_InvalidToken() throws Exception {
        SectionDTO section1 = new SectionDTO(1L, "sectionName1", 1L);
        SectionDTO section2 = new SectionDTO(2L, "sectionName2", 1L);
        given(sectionService.findAll()).willReturn(Arrays.asList(section1, section2));
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/sections")
                                                .header("Authorization", INVALID_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("getAllSections")
    void getAllSectionsThrowsEntitiesNotFoundException() throws Exception {
        given(sectionService.findAll()).willThrow(EntitiesNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/sections")
                                                    .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getSectionById")
    void getSectionById_RoleAdmin() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(sectionId, "sectionName", 1L);
        given(sectionService.findById(sectionId)).willReturn(section);
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/sections/{sectionId}", sectionId)
                                                .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionId").value(sectionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionName").value("sectionName"));

    }

    @Test
    @DisplayName("getSectionById")
    void getSectionById_RoleTeacher() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(sectionId, "sectionName", 1L);
        given(sectionService.findById(sectionId)).willReturn(section);
        ResultActions resultActions = mockMvc.perform(
                                                get("/learn/sections/{sectionId}", sectionId)
                                                .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionId").value(sectionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionName").value("sectionName"));
    }

    @Test
    @DisplayName("getSectionById")
    void getSectionById_RoleStudent() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(sectionId, "sectionName", 1L);
        given(sectionService.findById(sectionId)).willReturn(section);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/sections/{sectionId}", sectionId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionId").value(sectionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sectionName").value("sectionName"));
    }

    @Test
    @DisplayName("getSectionById")
    void getSectionByIdThrowsEntityNotFoundException() throws Exception {
        Long sectionId = 1L;
        given(sectionService.findById(sectionId)).willThrow(EntityNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/sections/{sectionId}", sectionId)
                                                    .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("createSection")
    void createSection_RoleAdmin() throws Exception {
        SectionDTO section = new SectionDTO(null, "sectionName", 1L);
        SectionDTO section2 = new SectionDTO(2L, "sectionName", 1L);
        given(sectionService.create(section)).willReturn(section2);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/sections")
                                                .header("Authorization", ADMIN_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(section)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isCreated())
                     .andExpect(MockMvcResultMatchers.jsonPath("$.sectionName").value("sectionName"));

    }

    @Test
    @DisplayName("createSection")
    void createSection_RoleTeacher() throws Exception {
        SectionDTO section = new SectionDTO(null, "sectionName", 1L);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/sections")
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(section)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("createSection")
    void createSectionThrowsConstraintViolationException() throws Exception {
        SectionDTO section = new SectionDTO(null, "", 1L);
        given(sectionService.create(section)).willThrow(ConstraintViolationException.class);
        ResultActions resultActions = mockMvc.perform(
                                            post("/learn/sections")
                                            .header("Authorization", ADMIN_TOKEN)
                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .content(new ObjectMapper().writeValueAsString(section)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("updateSection")
    void updateSection_RoleAdmin() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(null, "sectionName", 1L);
        SectionDTO section2 = new SectionDTO(sectionId, "sectionName", 1L);
        given(sectionService.updateById(sectionId, section)).willReturn(section2);
        ResultActions resultActions = mockMvc.perform(
                                                    put("/learn/sections/{sectionId}", sectionId)
                                                    .header("Authorization", ADMIN_TOKEN)
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(new ObjectMapper().writeValueAsString(section)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                     .andExpect(MockMvcResultMatchers.jsonPath("$.sectionId").value(sectionId))
                     .andExpect(MockMvcResultMatchers.jsonPath("$.sectionName").value("sectionName"));
    }

    @Test
    @DisplayName("updateSection")
    void updateSection_RoleTeacher() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(null, "sectionName", 1L);
        ResultActions resultActions = mockMvc.perform(
                                                    put("/learn/sections/{sectionId}", sectionId)
                                                    .header("Authorization", TEACHER_TOKEN)
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(new ObjectMapper().writeValueAsString(section)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteSectionById")
    void deleteSectionById_RoleAdmin() throws Exception {
        Long sectionId = 1L;
        SectionDTO section = new SectionDTO(sectionId, "sectionName", 1L);
        given(sectionService.deleteById(sectionId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                            delete("/learn/sections/{sectionId}", sectionId)
                                            .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                     .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                     .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }

    @Test
    @DisplayName("deleteSectionById")
    void deleteSectionById_RoleTeacher() throws Exception {
        Long sectionId = 1L;
        given(sectionService.deleteById(sectionId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/sections/{sectionId}", sectionId)
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteSectionById")
    void deleteSectionByIdThrowsEntityNotFoundException() throws Exception {
        Long sectionId = 1L;
        given(sectionService.deleteById(sectionId)).willThrow(EntityNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                            delete("/learn/sections/{sectionId}", sectionId)
                                            .header("Authorization", ADMIN_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

}

/*
    mvc.perform(get(PORT_SCAN_URL)
   .param("address", "192.168.1.100")
   .param("port", "53"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.response").exists())
            .andExpect(jsonPath("$.response.code", is(400)))
            .andExpect(jsonPath("$.response.errors[0].message", is("Site local IP is not supported")));

     */