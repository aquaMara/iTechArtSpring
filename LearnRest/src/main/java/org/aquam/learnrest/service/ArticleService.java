package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findById(Long articleId);
    List<Article> findAll();
    Article create(ArticleDTO articleDTO, MultipartFile file) throws IOException;
    Article create(ArticleDTO articleDTO);
    Article addFile(Long articleId, MultipartFile file) throws IOException;
    Article updateById(Long articleId, ArticleDTO newArticle, MultipartFile file) throws IOException;
    Article updateById(Long articleId, ArticleDTO newArticleDTO);
    boolean deleteById(Long articleId);
}
