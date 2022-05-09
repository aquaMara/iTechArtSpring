package org.aquam.learnrest.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;
    private String heading;
    @Column(columnDefinition = "TEXT")
    private String content;

    private String link;
    private String literature;

    private Double sumOfScores;
    private Integer timesClicked;
    private Double rating;

    public Article() {
        this.sumOfScores = 0.0;
        this.timesClicked = 0;
        this.rating = 0.0;
    }

    public Article(Long articleId, String heading, String content, String link, String literature) {
        this();
        this.articleId = articleId;
        this.heading = heading;
        this.content = content;
        this.link = link;
        this.literature = literature;
    }

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Section section;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(heading, article.heading) && Objects.equals(content, article.content) && Objects.equals(link, article.link) && Objects.equals(literature, article.literature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content, link, literature);
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleHeading='" + heading + '\'' +
                ", articleContent='" + content + '\'' +
                ", link='" + link + '\'' +
                ", literature='" + literature + '\'' +
                '}';
    }
}
