package org.aquam.learnrest.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sectionId;
    private String sectionName;

    public Section(Long sectionId, String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }



    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "section")
    List<Article> allArticles = new ArrayList<>();

    public void addArticle(Article article) {
        this.allArticles.add(article);
        article.setSection(this);
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(sectionName, section.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionName);
    }
}
