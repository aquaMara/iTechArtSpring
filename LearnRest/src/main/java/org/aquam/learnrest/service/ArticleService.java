package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findById(Long articleId);
    ArticleDTO findByIdDTO(Long articleIdDTO);
    List<ArticleDTO> findAllDTO();
    ArticleDTO createDTO(ArticleDTO articleDTO);
    Article addFile(Long articleId, MultipartFile file) throws IOException;
    ArticleDTO updateByIdDTO(Long articleId, ArticleDTO newArticleDTO);
    boolean deleteById(Long articleId);
}
