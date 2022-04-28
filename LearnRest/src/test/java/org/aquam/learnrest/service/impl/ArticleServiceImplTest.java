package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.UserRole;
import org.aquam.learnrest.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ModelMapper myModelMapper;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private SectionServiceImpl sectionService;
    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ImageUploaderImpl imageUploader;
    @Mock
    private MultipartFile multipartFile;

    public final String uploadDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\article_images";

    @Test
    @DisplayName("findById")
    void findByIdShouldReturnArticle() {
        Long articleId = 1L;
        Article article = new Article(articleId, "heading", "content", "link", "literature", "filePath.jpg");
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(article));
        articleService.findById(articleId);
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldThrowException() {
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> articleService.findById(anyLong()));
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfArticles() {
        Article article = new Article(1L, "heading", "content", "link", "literature", "filePath.jpg");
        given(articleRepository.findAll())
                .willReturn(Arrays.asList(article));
        articleService.findAll();
        then(articleRepository).should(times(2)).findAll();

    }

    @Test
    @DisplayName("findAll")
    void findAllShouldThrowEntitiesNotFoundException() {
        given(articleRepository.findAll())
                .willReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> articleService.findAll());
    }

    @Test
    @Disabled
    @DisplayName("create")
    void createWithoutMultipartShouldReturnArticle() throws IOException {
        Article article = new Article(null, "heading", "content", "link", "literature", "filePath.jpg");
        ArticleDTO articleDTO = new ArticleDTO(null, "heading", "content", "link", "literature", "filepath.jpg", 1L, 1L);
        given(myModelMapper.map(articleDTO, Article.class)).willReturn(article);
        articleService.create(articleDTO);
        then(articleRepository).should().save(article);
    }

    @Test
    @Disabled
    @DisplayName("create")
    void createShouldReturnArticle() throws IOException {
        Article article = new Article(null, "heading", "content", "link", "literature", "filePath.jpg");
        ArticleDTO articleDTO = new ArticleDTO(null, "heading", "content", "link", "literature", "filepath.jpg", 1L, 1L);

        given(myModelMapper.map(articleDTO, Article.class)).willReturn(article);
        //given(articleService.toArticle(articleDTO)).willReturn(article);
        given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());

        articleService.create(articleDTO, multipartFile);
        then(articleRepository).should().save(article);
    }

    @Test
    @DisplayName("create")
    void createShouldThrowConstraintViolationException() {
        ArticleDTO articleDTO = new ArticleDTO(null, null, null, "link", "literature","filepath.jpg", 1L, 1L);
        assertThrows(ConstraintViolationException.class,
                () -> articleService.toArticle(articleDTO));
    }

    // ??? article is null
    @Disabled
    @Test
    @DisplayName("create")
    void createShouldNotThrowConstraintViolationException() throws IOException {
        ArticleDTO articleDTO = new ArticleDTO(null, "heading", "content", null, null,"filepath.jpg", 1L, 1L);
        Article article = new Article(null, "heading", "content", null, null, "filePath.jpg");

        given(myModelMapper.map(articleDTO, Article.class)).willReturn(article);
        given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());
        articleService.create(articleDTO, multipartFile);
        then(articleRepository).should().save(article);
    }

    @Test
    void updateByIdShouldReturnArticle() throws IOException {
        Article newArticle = new Article(1L, "content", "heading", "link", "literature", "filepath.jpg");
        ArticleDTO newArticleDTO = new ArticleDTO(1L, "content", "heading", "link", "literature", "filepath.jpg", 2L, 3L);
        given(myModelMapper.map(newArticleDTO, Article.class)).willReturn(newArticle);
        // given(imageUploader.uploadImage(any(MultipartFile.class), anyString())).willReturn(anyString());
        // given(imageUploader.uploadImage(multipartFile, uploadDirectory)).willReturn("anyString()");
        articleService.updateById(1L, newArticleDTO, multipartFile);
        then(articleRepository).should().save(newArticle);
    }

    @Test
    void updateByIdWithoutMultipartShouldReturnArticle() throws IOException {
        Long articleId = 1L;
        Article article = new Article(articleId, "content", "heading", "link", "literature", "filepath.jpg");
        Article newArticle = new Article(1L, "content", "heading", "link", "literature", "filepath.jpg");
        ArticleDTO newArticleDTO = new ArticleDTO(1L, "content", "heading", "link", "literature", "filepath.jpg", 2L, 3L);
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        given(myModelMapper.map(newArticleDTO, Article.class)).willReturn(newArticle);
        articleService.updateById(1L, newArticleDTO);
        then(articleRepository).should().save(newArticle);
    }

    @Test
    void deleteById() {
        Long articleId = 1L;
        Article article = new Article(articleId, "content", "heading", "link", "literature", "filepath.jpg");
        given(articleRepository.findById(1L))
                .willReturn(Optional.of(article))
                .willReturn(Optional.of(article));
        articleService.deleteById(articleId);
        then(articleRepository).should().delete(article);

    }

    @Test
    void toArticle() {
    }

}