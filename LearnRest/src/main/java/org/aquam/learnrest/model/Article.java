package org.aquam.learnrest.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
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
    private Integer timesClicked;
    private Integer rating;

    private String filePath;

    public Article(Long articleId, String heading, String content, String link, String literature, String filePath) {
        this.articleId = articleId;
        this.heading = heading;
        this.content = content;
        this.link = link;
        this.literature = literature;
        this.filePath = filePath;
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
        return Objects.equals(heading, article.heading) && Objects.equals(content, article.content) && Objects.equals(link, article.link) && Objects.equals(literature, article.literature) && Objects.equals(filePath, article.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content, link, literature, filePath);
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleHeading='" + heading + '\'' +
                ", articleContent='" + content + '\'' +
                ", link='" + link + '\'' +
                ", literature='" + literature + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
