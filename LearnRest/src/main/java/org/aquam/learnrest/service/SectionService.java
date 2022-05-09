package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;

import java.util.List;

public interface SectionService {

    Section findByIdBase(Long sectionId);
    SectionDTO findById(Long sectionId);
    List<SectionDTO> findAll();
    SectionDTO create(SectionDTO sectionDTO);
    SectionDTO updateById(Long sectionId, SectionDTO newSectionDTO);
    boolean deleteById(Long sectionId);

}
