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
    public List<Section> findAll() {
        if (sectionRepository.findAll().isEmpty())
            throw new EntityNotFoundException("There are no sections");
        return sectionRepository.findAll();
    }

    @Override
    public Section create(SectionDTO sectionDTO) {
        Section section = toSection(sectionDTO);
        return sectionRepository.save(section);
        // Subject subject = subjectService.findById(sectionDTO.getSubjectId());
        //        section.setSubject(subject);
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
