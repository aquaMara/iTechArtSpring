package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.ArticleDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.Article;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.repository.ArticleRepository;
import org.aquam.learnrest.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;
    private final SectionServiceImpl sectionService;

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }


    @Override
    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(()
                -> new EntityNotFoundException("Article with id: " + articleId + " not found"));
    }

    @Override
    public ArticleDTO findByIdDTO(Long articleId) {
        Article article = findById(articleId);
        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
        return articleDTO;
    }

    @Override
    public List<ArticleDTO> findAllDTO() {
        if (articleRepository.findAll().isEmpty())
            throw new EntitiesNotFoundException("There are no articles");
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOS = articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class)).collect(Collectors.toList());
        return articleDTOS;
    }

    // links, literature are not necessary
    // проверка на пользователя
    // получается много репозиториев внутри
    @Override
    public ArticleDTO createDTO(ArticleDTO articleDTO) {
        Article article = toArticle(articleDTO);
        Article articleFromRepository = articleRepository.save(article);
        ArticleDTO articleDTOFromRepository = modelMapper.map(articleFromRepository, ArticleDTO.class);
        return articleDTOFromRepository;
    }

    @Override
    public ArticleDTO updateByIdDTO(Long articleId, ArticleDTO newArticleDTO) {
        Article article = findById(articleId);
        Article newArticle = toArticle(newArticleDTO);
        newArticle.setArticleId(articleId);
        Article articleFromRepository = articleRepository.save(newArticle);
        ArticleDTO articleDTOFromRepository = modelMapper.map(articleFromRepository, ArticleDTO.class);
        return articleDTOFromRepository;
    }

    @Override
    public ArticleDTO updateById(Long articleId, ArticleDTO newArticleDTO) {
        Article article = findById(articleId);

        newArticleDTO.setUserId(article.getUser().getUserId());
        newArticleDTO.setSectionId(article.getSection().getSectionId());

        Article newArticle = toArticle(newArticleDTO);
        article.setContent(newArticle.getContent());
        article.setHeading(newArticle.getHeading());
        article.setLink(newArticle.getLink());
        article.setLiterature(newArticle.getLiterature());

        Article articleFromRepository = articleRepository.save(article);
        ArticleDTO articleDTOFromRepository = modelMapper.map(articleFromRepository, ArticleDTO.class);
        return articleDTOFromRepository;
    }

    @Override
    public boolean deleteById(Long articleId) {
        Article article = findById(articleId);
        articleRepository.delete(article);
        return true;
    }

    @Override
    public ArticleDTO setRating(Long articleId, Double score) {
        Article article = findById(articleId);
        Double newSumOfScores = article.getSumOfScores() + score;
        article.setSumOfScores(newSumOfScores);
        Integer newTimesClicked = article.getTimesClicked() + 1;
        article.setTimesClicked(newTimesClicked);
        Double rating = newSumOfScores * 1.0 / newTimesClicked;
        article.setRating(rating);
        Article articleFromRepository = articleRepository.save(article);
        ArticleDTO articleDTOFromRepository = modelMapper.map(articleFromRepository, ArticleDTO.class);
        return articleDTOFromRepository;
    }

    public Article toArticle(ArticleDTO articleDTO) {
        Set<ConstraintViolation<ArticleDTO>> validationExceptions = validator.validate(articleDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        Article article = modelMapper.map(articleDTO, Article.class);
        Section section = sectionService.findById(articleDTO.getSectionId());
        article.setSection(section);
        AppUser user = userService.findById(articleDTO.getUserId());
        article.setUser(user);
        return article;
        /*
        if (articleDTO.getLiterature() != null)
            article.setLiterature(articleDTO.getLiterature());
        if (articleDTO.getLink() != null)
            article.setLink(articleDTO.getLink());
         */
    }
}



/*
if (file == null || article.getHeading() == null || article.getContent() == null ||
            sectionId == null || user.getUserId() == null)
            throw new NullPointerException("Not all the fields are sent");
 */
/*
if (articleDTO.getSectionId() == null || articleDTO.getUserId() == null
                || articleDTO.getHeading() == null || articleDTO.getContent() == null)
            throw new NullPointerException("Some fields are null");
 */