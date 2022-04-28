package org.aquam.learnrest.dto.mapper;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionMapper {

    public Section toSection(SectionDTO sectionDTO) {
        if (sectionDTO.getSectionName() == null || sectionDTO.getSubjectId() == null)   //  || sectionDTO.getSubjectName() == null
            throw new NullPointerException("Section or subject is null");
        if (sectionDTO.getSectionName().isBlank()) //  || sectionDTO.getSubjectName().isBlank()
            throw new EmptyInputException("Section or subject name is blank");
        Section section = new Section();
        section.setSectionName(sectionDTO.getSectionName());
        return section;
    }

}
