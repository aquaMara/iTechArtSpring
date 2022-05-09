package org.aquam.learnrest.service;

import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.model.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubjectService {

    Subject findByIdBase(Long subjectId);
    SubjectDTO findById(Long subjectId);
    Subject findByNameBase(String name);
    SubjectDTO findByName(String name);
    List<SubjectDTO> findAll();
    SubjectDTO create(SubjectDTO subjectDTO);
    Subject addFile(Long subjectId, MultipartFile file) throws IOException;
    SubjectDTO updateById(Long subjectId, SubjectDTO newSubjectDTO);
    boolean deleteById(Long subjectId);
}
