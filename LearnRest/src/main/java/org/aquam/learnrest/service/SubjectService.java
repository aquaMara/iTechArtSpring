package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.model.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

    Subject findById(Long subjectId);
    SubjectDTO findByIdDTO(Long subjectId);
    Subject findByName(String name);
    SubjectDTO findByNameDTO(String name);
    List<Subject> findAll();
    List<SubjectDTO> findAllDTO();
    Subject create(SubjectDTO subjectDTO, MultipartFile file) throws IOException;
    Subject create(SubjectDTO subjectDTO);
    SubjectDTO createDTO(SubjectDTO subjectDTO);
    Subject addFile(Long subjectId, MultipartFile file) throws IOException;
    Subject updateById(Long subjectId, SubjectDTO newSubjectDTO, MultipartFile file) throws IOException;
    Subject updateById(Long subjectId, SubjectDTO newSubjectDTO);
    SubjectDTO updateByIdDTO(Long subjectId, SubjectDTO newSubjectDTO);
    boolean deleteById(Long subjectId);
}
