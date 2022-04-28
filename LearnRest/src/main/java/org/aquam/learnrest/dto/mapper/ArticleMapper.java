package org.aquam.learnrest.dto.mapper;

import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article toArticle(ArticleDTO articleDTO) {
        if (articleDTO.getSectionId() == null || articleDTO.getUserId() == null
            || articleDTO.getHeading() == null || articleDTO.getContent() == null)
            throw new NullPointerException("Some fields are null");
        if (articleDTO.getHeading().isBlank() || articleDTO.getContent().isBlank()
            || articleDTO.getLink().isBlank() || articleDTO.getLiterature().isBlank())
            throw new EmptyInputException("Some fields are blank");
        Article article = new Article();
        article.setHeading(articleDTO.getHeading());
        article.setContent(articleDTO.getContent());
        if (articleDTO.getLiterature() != null)
            article.setLiterature(articleDTO.getLiterature());
        if (articleDTO.getLink() != null)
            article.setLink(articleDTO.getLink());
        return  article;
    }

}
