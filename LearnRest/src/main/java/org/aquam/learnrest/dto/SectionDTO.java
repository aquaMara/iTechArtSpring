package org.aquam.learnrest.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SectionDTO {

    private Long sectionId;
    @NotBlank(message = "Can not be empty")
    private String sectionName;

    private Long subjectId;
    List<ArticleDTO> articles;

    public SectionDTO(Long sectionId, String sectionName, Long subjectId) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionDTO that = (SectionDTO) o;
        return Objects.equals(sectionName, that.sectionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionName);
    }
}
