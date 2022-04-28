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
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subjectId;
    @Column(unique = true)
    private String subjectName;
    private String filePath;

    public Subject(Long subjectId, String subjectName, String filePath) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.filePath = filePath;
    }

    @OneToMany(mappedBy = "subject")
    List<Section> allSections = new ArrayList<>();

    public void addSection(Section section) {
        this.allSections.add(section);
        section.setSubject(this);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectName, subject.subjectName) && Objects.equals(filePath, subject.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName, filePath);
    }
}
