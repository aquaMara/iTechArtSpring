package org.aquam.learnrest.service.impl;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ImageUploaderImpl imageUploader;
    @InjectMocks
    private SubjectServiceImpl subjectService;
    @Mock
    private MultipartFile multipartFile;

    @Test
    @DisplayName("findById")
    void findByIdShouldReturnSubject() {
        // Arrange - Given
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        given(subjectRepository.findById(subjectId)).willReturn(java.util.Optional.of(subject));
        // Act - When
        subjectService.findById(subjectId);
        // Assert - Then
        then(subjectRepository).should().findById(subjectId);
    }


    @Test
    @DisplayName("findById")
    void findByIdShouldThrowEntityNotFoundException() {
        given(subjectRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> subjectService.findById(anyLong()));
    }


    @Test
    @DisplayName("findByName")
    void findByNameShouldReturnSubject() {
        String subjectName = "subject_name";
        Subject subject = new Subject(1L, subjectName, "filepath.jpg");
        given(subjectRepository.findBySubjectName(subjectName))
                .willReturn(java.util.Optional.of(subject));
        subjectService.findByName(subjectName);
        then(subjectRepository).should().findBySubjectName(subjectName);
    }


    @Test
    @DisplayName("findByName")
    void findByNameShouldThrowEntityNotFoundException() {
        given(subjectRepository.findBySubjectName(anyString())).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> subjectService.findByName(anyString()));
    }


    @Test
    @DisplayName("findAll")
    void findAllShouldReturnListOfSubjects() {
        List<Subject> subjects = List.of(new Subject(1L, "subject_name", "filepath.jpg"));
        given(subjectRepository.findAll()).willReturn(subjects);
        subjectService.findAll();
        then(subjectRepository).should(times(2)).findAll();
    }


    @Test
    @DisplayName("findAll")
    void findAllShouldThrowEntitiesNotFoundException() {
        given(subjectRepository.findAll()).willReturn(new ArrayList<>());
        assertThrows(EntitiesNotFoundException.class,
                () -> subjectService.findAll());
    }


    // need to check
    // кажется, что слишком много махинаций с given - willReturn, так и должно быть?
    @Test
    @DisplayName("create")
    void createShouldReturnSubject() throws IOException {
        String newSubjectName = "subject_name";
        Subject subject = new Subject(null, newSubjectName, "filepath.jpg");
        SubjectDTO subjectDTO = new SubjectDTO(null, newSubjectName);
        given(modelMapper.map(subjectDTO, Subject.class)).willReturn(subject);
        subjectService.create(subjectDTO);
        then(subjectRepository).should().save(subject);
    }


    @Test
    @DisplayName("create")
    void createShouldThrowEntityExistsException() throws IOException {
        String subjectName = "subject_name";
        given(subjectRepository.findBySubjectName(subjectName))
                .willReturn(Optional.of(new Subject(1L, subjectName, "filepath.jpg")));
        assertThrows(EntityExistsException.class,
                () -> subjectService.create(new SubjectDTO(null, subjectName)));
    }


    @Test
    @DisplayName("create")
    void createShouldThrowConstraintViolationException() throws IOException {
        SubjectDTO subjectDTO = new SubjectDTO(null, null);
        assertThrows(ConstraintViolationException.class,
                () -> subjectService.toSubject(subjectDTO));
    }

    /*
    // -
    @Test
    @DisplayName("updateById")
    void updateByIdShouldThrowEntityExistsException() throws IOException {
        Long subjectId = 1L;
        String subjectName = "subject_name";
        Subject newSubject = new Subject(subjectId, "subject_name", "filepath.jpg");
        SubjectDTO newSubjectDTO = new SubjectDTO(null, "subject_name");
        given(subjectRepository.findById(subjectId)).willReturn(Optional.of(new Subject(1L, "sn", "filepath.jpg")));
        given(subjectRepository.findBySubjectName(newSubjectDTO.getSubjectName())).willReturn(Optional.of(newSubject));
        assertThrows(EntityExistsException.class,
                () -> subjectService.updateById(subjectId, newSubjectDTO));
    }
     */

    @Test
    @DisplayName("updateById")
    void updateByIdShouldReturnSubject() {
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        given(subjectRepository.findById(subjectId)).willReturn(Optional.of(subject));
        Subject newSubject = new Subject(subjectId, "subject_name2", "filepath.jpg");
        SubjectDTO newSubjectDTO = new SubjectDTO(subjectId, "subject_name2");
        given(modelMapper.map(newSubjectDTO, Subject.class)).willReturn(newSubject);
        subjectService.updateById(subjectId, newSubjectDTO);
        then(subjectRepository).should().save(newSubject);
    }

    @Test
    void deleteByIdShouldReturnTrue() {
        Long subjectId = 1L;
        Subject subject = new Subject(subjectId, "subject_name", "filepath.jpg");
        given(subjectRepository.findById(subjectId))
                .willReturn(Optional.of(subject));
        subjectService.deleteById(subjectId);
        then(subjectRepository).should().delete(subject);
    }

    @Test
    void deleteByIdShouldThrowEntityNotFoundException() {
        Long subjectId = 1L;
        given(subjectRepository.findById(subjectId)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> subjectService.deleteById(subjectId));

    }
}