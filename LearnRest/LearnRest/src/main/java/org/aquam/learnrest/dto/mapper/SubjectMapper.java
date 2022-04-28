package org.aquam.learnrest.dto.mapper;

import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public Subject mapToSubject(SubjectDTO subjectDTO) {
        if (subjectDTO.getSubjectName() == null)
            throw new NullPointerException("Subject is null");
        if (subjectDTO.getSubjectName().isBlank())
            throw new EmptyInputException("Subject name is blank");
        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        return subject;
    }

    public SubjectDTO mapToSubjectDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectName(subject.getSubjectName());
        return subjectDTO;
    }

}
