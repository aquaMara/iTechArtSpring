package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SectionRepository;
import org.aquam.learnrest.service.SectionService;
import org.aquam.learnrest.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;
    private final SubjectServiceImpl subjectService;

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public Section findById(Long sectionId) {
        return sectionRepository.findById(sectionId).orElseThrow(()
                -> new EntityNotFoundException("Section with id: " + sectionId + " not found"));
    }

    @Override
    public SectionDTO findByIdDTO(Long sectionId) {
        Section section = findById(sectionId);
        SectionDTO sectionDTO = modelMapper.map(section, SectionDTO.class);
        return sectionDTO;
    }

    @Override
    public List<Section> findAll() {
        if (sectionRepository.findAll().isEmpty())
            throw new EntityNotFoundException("There are no sections");
        return sectionRepository.findAll();
    }

    @Override
    public List<SectionDTO> findAllDTO() {
        List<Section> sections = findAll();
        List<SectionDTO> sectionDTOS = sections.stream()
                .map(section -> modelMapper.map(section, SectionDTO.class)).collect(Collectors.toList());
        return sectionDTOS;
    }

    @Override
    public Section create(SectionDTO sectionDTO) {
        Section section = toSection(sectionDTO);
        return sectionRepository.save(section);
        // Subject subject = subjectService.findById(sectionDTO.getSubjectId());
        //        section.setSubject(subject);
    }

    @Override
    public SectionDTO createDTO(SectionDTO sectionDTO) {
        Section section = toSection(sectionDTO);
        Section sectionFromRepository = sectionRepository.save(section);
        SectionDTO sectionDTOFromRepository = modelMapper.map(sectionFromRepository, SectionDTO.class);
        return sectionDTOFromRepository;
    }

    @Override
    public Section updateById(Long sectionId, SectionDTO newSectionDTO) {
        Section section = findById(sectionId);
        Section newSection = toSection(newSectionDTO);
        return sectionRepository.save(newSection);
        //Section section = findById(sectionId);
        //section.setSectionName(newSection.getSectionName());
        // Subject subject = subjectService.findById(newSectionDTO.getSubjectId());
        // section.setSubject(subject);
        // return sectionRepository.save(section);
    }

    @Override
    public SectionDTO updateByIdDTO(Long sectionId, SectionDTO newSectionDTO) {
        Section section = findById(sectionId);
        Section newSection = toSection(newSectionDTO);
        Section sectionFromRepository = sectionRepository.save(newSection);
        SectionDTO sectionDTOFromRepository = modelMapper.map(sectionFromRepository, SectionDTO.class);
        return sectionDTOFromRepository;
    }

    @Override
    public boolean deleteById(Long sectionId) {
        Section section = findById(sectionId);
        sectionRepository.delete(section);
        return true;
    }

    public Section toSection(SectionDTO sectionDTO) {
        Set<ConstraintViolation<SectionDTO>> validationExceptions = validator.validate(sectionDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        Section section = modelMapper.map(sectionDTO, Section.class);
        Subject subject = subjectService.findById(sectionDTO.getSubjectId());
        section.setSubject(subject);
        return section;
    }
}
