package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;

import java.util.List;

public interface SectionService {

    Section findById(Long sectionId);
    SectionDTO findByIdDTO(Long sectionId);
    List<SectionDTO> findAllDTO();
    SectionDTO createDTO(SectionDTO sectionDTO);
    SectionDTO updateByIdDTO(Long sectionId, SectionDTO newSectionDTO);
    boolean deleteById(Long sectionId);

}
