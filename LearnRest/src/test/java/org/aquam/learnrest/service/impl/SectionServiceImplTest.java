package org.aquam.learnrest.service.impl;

import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SectionRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

// на права
@ExtendWith(MockitoExtension.class)
class SectionServiceImplTest {

    /*
    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private SubjectServiceImpl subjectService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SectionServiceImpl sectionService;

    // @DisplayName("given - New Movies, when - Get New Movies With Discount Method Is Called, Then - New Movies With Discount Is Returned")
    @Test
    @DisplayName("findById")
    void findByIdShouldReturnSection() {
        Long sectionId = 1L;
        Section section = new Section(sectionId, "section_name");
        given(sectionRepository.findById(sectionId)).willReturn(java.util.Optional.of(section));
        sectionService.findById(sectionId);
        then(sectionRepository).should().findById(sectionId);
    }

    @Test
    @DisplayName("findById")
    void findByIdShouldThrowException() {
        given(sectionRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> sectionService.findById(anyLong()));
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfSections() {
        given(sectionRepository.findAll())
                .willReturn(Arrays.asList(new Section(1L, "section_name")));
        sectionService.findAll();
        then(sectionRepository).should(times(2)).findAll();
    }

    @Test
    @DisplayName("findAll")
    void findAllShouldThrowException() {
        given(sectionRepository.findAll()).willReturn(new ArrayList<>());
        assertThrows(EntityNotFoundException.class,
                () -> sectionService.findAll());
    }

    @Test
    @DisplayName("create")
    void createShouldReturnCreatedSection() {
        SectionDTO sectionDTO = new SectionDTO(null, "section_name", 1L);
        Section section = new Section(null, "section_name");
        given(modelMapper.map(sectionDTO, Section.class)).willReturn(section);
        sectionService.create(sectionDTO);
        then(sectionRepository).should().save(section);
    }

    @Test
    @DisplayName("create")
    void createShouldThrowConstraintViolationException() {
        SectionDTO sectionDTO = new SectionDTO(null, null, 1L);
        assertThrows(ConstraintViolationException.class,
                () -> sectionService.create(sectionDTO));
    }

    @Test
    @DisplayName("update")
    void updateByIdShouldReturnUpdatedSection() {
        Long sectionId = 1L;
        Section section = new Section(sectionId, "section_name");
        SectionDTO newSectionDTO = new SectionDTO(sectionId, "section_name", 1L);
        Section newSection = new Section(sectionId, "section_name");
        given(sectionRepository.findById(sectionId)).willReturn(Optional.of(section));
        given(modelMapper.map(newSectionDTO, Section.class)).willReturn(newSection);
        sectionService.updateById(sectionId, newSectionDTO);
        then(sectionRepository).should().save(newSection);
    }

    @Test
    @DisplayName("deleteById")
    void deleteById() {
        Long sectionId = 2L;
        Section section = new Section(sectionId, "section_name");
        given(sectionRepository.findById(sectionId))
                .willReturn(java.util.Optional.of(section))
                .willReturn(java.util.Optional.of(section));
        sectionService.deleteById(sectionId);
        then(sectionRepository).should().delete(section);
    }

    @Disabled
    @Test
    @DisplayName("toSection")
    void toSection() {

    }

     */
}