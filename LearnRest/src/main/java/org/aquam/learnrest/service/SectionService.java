package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;

import java.util.List;

public interface SectionService {

    Section findById(Long sectionId);
    SectionDTO findByIdDTO(Long sectionId);
    List<Section> findAll();
    List<SectionDTO> findAllDTO();
    Section create(SectionDTO sectionDTO);
    SectionDTO createDTO(SectionDTO sectionDTO);
    Section updateById(Long sectionId, SectionDTO newSectionDTO);
    SectionDTO updateByIdDTO(Long sectionId, SectionDTO newSectionDTO);
    boolean deleteById(Long sectionId);

}
