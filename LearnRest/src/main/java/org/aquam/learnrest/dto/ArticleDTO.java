package org.aquam.learnrest.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleDTO {

    private Long articleId;
    @NotBlank(message = "Can not be empty")
    private String heading;
    @NotBlank(message = "Can not be empty")
    private String content;
    private String link;
    private String literature;
    private String filePath;

    private Long sectionId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDTO that = (ArticleDTO) o;
        return heading.equals(that.heading) && content.equals(that.content) && Objects.equals(link, that.link) && Objects.equals(literature, that.literature) && filePath.equals(that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content, link, literature, filePath);
    }
}