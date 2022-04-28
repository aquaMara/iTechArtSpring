package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.service.impl.ArticleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learn/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping
    ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/{articleId}")
    ResponseEntity<Article> getArticleById(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleService.findById(articleId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("")
    ResponseEntity<Article> createArticle(@AuthenticationPrincipal AppUser user, @RequestBody ArticleDTO articleDTO) {
        articleDTO.setUserId(user.getUserId());
        return new ResponseEntity<>(articleService.create(articleDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/upload/{articleId}")
    ResponseEntity<Article> addFile(@PathVariable Long articleId, @RequestParam(value = "data") MultipartFile file) throws IOException {
        // user was set higher
        return new ResponseEntity<>(articleService.addFile(articleId, file), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{articleId}")
    ResponseEntity<Article> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDTO articleDTO) throws IOException {
        return new ResponseEntity<>(articleService.updateById(articleId, articleDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{articleId}")
    ResponseEntity<Boolean> deleteArticleById(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleService.deleteById(articleId), HttpStatus.OK);
    }

}
/*
get, post = /api/articles
get, put, delete = /api/articles/{id}
 */

/*
@PreAuthorize("hasRole('TEACHER')")
    @PostMapping("")
    ResponseEntity<Article> createArticle(@AuthenticationPrincipal AppUser user, ArticleDTO articleDTO, MultipartFile file) throws IOException {
        articleDTO.setUserId(user.getUserId());
        return new ResponseEntity<>(articleService.create(articleDTO, file), HttpStatus.CREATED);
    }
 */
