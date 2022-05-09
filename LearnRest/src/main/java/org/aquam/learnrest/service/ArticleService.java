package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findByIdBase(Long articleId);
    ArticleDTO findById(Long articleIdDTO);
    List<ArticleDTO> findAll();
    ArticleDTO create(ArticleDTO articleDTO);
    ArticleDTO updateById(Long articleId, ArticleDTO newArticleDTO);
    boolean deleteById(Long articleId);

    ArticleDTO setRating(Long articleId, Double score);
}
