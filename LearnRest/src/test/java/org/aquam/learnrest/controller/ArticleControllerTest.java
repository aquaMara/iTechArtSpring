package org.aquam.learnrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aquam.learnrest.config.WebSecurityConfig;
import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ArticleController.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@AutoConfigureMockMvc
class ArticleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleServiceImpl articleService;

    @InjectMocks
    private ArticleController articleController;

    private static final String ADMIN_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjY2Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJpYXQiOjE2NTIxNzgzMTcsImV4cCI6MTY1MjE4MTkxN30.YnUMLqvwKW7kpXXtL9ibPK5AGUcHDAbcT9fbFEJK4b4";
    private static final String TEACHER_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbSIsInJvbGUiOiJST0xFX1RFQUNIRVIiLCJpYXQiOjE2NTIxNzgyOTMsImV4cCI6MTY1MjE4MTg5M30.1L6fVKHkm4pUk6VMxAyS62NQ8RRewe5smI_N0dUrEH0";
    private static final String STUDENT_TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcXVhbWFyYSIsInJvbGUiOiJST0xFX1NUVURFTlQiLCJpYXQiOjE2NTIxNzgwMDAsImV4cCI6MTY1MjE4MTYwMH0.kGRvAiyoUhjPQuZiFcO7eYQAwJqcpHPrlu9PEZup_3Y";
    private static final String INVALID_TOKEN = "Bearer " + "not an actual token";

    @Test
    @DisplayName("getAllArticles")
    void getAllArticles_Invalid() throws Exception {
        ArticleDTO article1 = new ArticleDTO(1L, "heading1", "content1", "link1", "literature1", 0.0, 0, 0.0, 1L, 1L);
        ArticleDTO article2 = new ArticleDTO(2L, "heading2", "content2", "link2", "literature2", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.findAll()).willReturn(Arrays.asList(article1, article2));
        ResultActions resultActions = mockMvc.perform(
                get("/learn/articles")
                        .header("Authorization", INVALID_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("getAllArticles")
    void getAllArticles_RoleTeacher() throws Exception {
        ArticleDTO article1 = new ArticleDTO(1L, "heading1", "content1", "link1", "literature1", 0.0, 0, 0.0, 1L, 1L);
        ArticleDTO article2 = new ArticleDTO(2L, "heading2", "content2", "link2", "literature2", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.findAll()).willReturn(Arrays.asList(article1, article2));
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles")
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].articleId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].heading").value("heading1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].articleId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].literature").value("literature2"));
    }

    @Test
    @DisplayName("getAllArticles")
    void getAllArticles_RoleStudent() throws Exception {
        ArticleDTO article1 = new ArticleDTO(1L, "heading1", "content1", "link1", "literature1", 0.0, 0, 0.0, 1L, 1L);
        ArticleDTO article2 = new ArticleDTO(2L, "heading2", "content2", "link2", "literature2", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.findAll()).willReturn(Arrays.asList(article1, article2));
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles")
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].articleId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].heading").value("heading1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].articleId").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].literature").value("literature2"));

    }

    @Test
    @DisplayName("getAllArticles")
    void getAllArticlesThrowsEntitiesNotFoundException() throws Exception {
        given(articleService.findAll()).willThrow(EntitiesNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles")
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getArticleById")
    void getArticleById_RoleTeacher() throws Exception {
        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO(articleId, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.findById(articleId)).willReturn(article);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(articleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.heading").value("heading"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value("link"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.literature").value("literature"));
    }

    @Test
    @DisplayName("getArticleById")
    void getArticleById_RoleStudent() throws Exception {
        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO(articleId, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.findById(articleId)).willReturn(article);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(articleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.heading").value("heading"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value("link"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.literature").value("literature"));
    }

    @Test
    @DisplayName("getArticleById")
    void getArticleByIdThrowsEntityNotFoundException() throws Exception {
        Long articleId = 1L;
        given(articleService.findById(articleId)).willThrow(EntityNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    get("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("createArticle")
    void createArticle_RoleTeacher() throws Exception {
        ArticleDTO article = new ArticleDTO(null, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        ArticleDTO article2 = new ArticleDTO(1L, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.create(article)).willReturn(article2);
        ResultActions resultActions = mockMvc.perform(
                                                post("/learn/articles")
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(article)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.heading").value("heading"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value("link"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.literature").value("literature"));
    }

    @Test
    @DisplayName("createArticle")
    void createArticle_RoleStudent() throws Exception {
        ArticleDTO article = new ArticleDTO(null, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        ResultActions resultActions = mockMvc.perform(
                                                    post("/learn/articles")
                                                    .header("Authorization", STUDENT_TOKEN)
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(new ObjectMapper().writeValueAsString(article)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("updateArticle")
    void updateArticle_RoleTeacher() throws Exception {
        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO(null, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        ArticleDTO article2 = new ArticleDTO(1L, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.updateById(articleId, article)).willReturn(article2);
        ResultActions resultActions = mockMvc.perform(
                                                put("/learn/articles/{articleId}", articleId)
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(article)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.heading").value("heading"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value("link"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.literature").value("literature"));
    }

    @Test
    @DisplayName("updateArticle")
    void updateArticle_RoleStudent() throws Exception {
        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO(null, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        ResultActions resultActions = mockMvc.perform(
                                                    put("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", STUDENT_TOKEN)
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                    .content(new ObjectMapper().writeValueAsString(article)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("updateArticle")
    void updateArticleThrowsConstraintViolationException() throws Exception {
        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO(null, "heading", "content", "link", "literature", 0.0, 0, 0.0, 1L, 1L);
        given(articleService.updateById(articleId, article)).willThrow(ConstraintViolationException.class);
        ResultActions resultActions = mockMvc.perform(
                                                put("/learn/articles/{articleId}", articleId)
                                                .header("Authorization", TEACHER_TOKEN)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(new ObjectMapper().writeValueAsString(article)));
        resultActions.andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deleteArticleById")
    void deleteArticleById_RoleTeacher() throws Exception {
        Long articleId = 1L;
        given(articleService.deleteById(articleId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }

    @Test
    @DisplayName("deleteArticleById")
    void deleteArticleById_RoleStudent() throws Exception {
        Long articleId = 1L;
        given(articleService.deleteById(articleId)).willReturn(true);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", STUDENT_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("deleteArticleById")
    void deleteArticleByIdThrowsEntityNotFoundException() throws Exception {
        Long articleId = 1L;
        given(articleService.deleteById(articleId)).willThrow(EntityNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(
                                                    delete("/learn/articles/{articleId}", articleId)
                                                    .header("Authorization", TEACHER_TOKEN));
        resultActions.andDo(print());
        resultActions.andExpect(status().isNotFound());
    }

}