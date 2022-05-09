package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.exception.EntitiesNotFoundException;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.repository.SubjectRepository;
import org.aquam.learnrest.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    private final ImageUploaderImpl imageUploader;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static";

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public Subject findByIdBase(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(()
                -> new EntityNotFoundException("Subject with id: " + subjectId + " not found"));
    }

    @Override
    public SubjectDTO findById(Long subjectId) {
        Subject subject = findByIdBase(subjectId);
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        return subjectDTO;
    }

    @Override
    public Subject findByNameBase(String name) {
        return subjectRepository.findBySubjectName(name).orElseThrow(()
                -> new EntityNotFoundException("Subject with name: " + name + " not found"));
    }

    @Override
    public SubjectDTO findByName(String name) {
        Subject subject = findByNameBase(name);
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        return subjectDTO;
    }

    @Override
    public List<SubjectDTO> findAll() {
        if (subjectRepository.findAll().isEmpty())
            throw new EntitiesNotFoundException("There are no subjects");
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDTO> subjectDTOS = subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class)).collect(Collectors.toList());
        return subjectDTOS;
    }

    // на фронт недейств пока файл не выбран
    @Override
    public SubjectDTO create(SubjectDTO subjectDTO) {
        if (subjectRepository.findBySubjectName(subjectDTO.getSubjectName()).isPresent())
            throw new EntityExistsException("Subject with name: " + subjectDTO.getSubjectName() + " already exists");
        Subject subject = toSubject(subjectDTO);
        Subject subjectFromRepository = subjectRepository.save(subject);
        SubjectDTO subjectDTOFromRepository = modelMapper.map(subjectFromRepository, SubjectDTO.class);
        return subjectDTOFromRepository;
    }

    @Override
    public Subject addFile(Long subjectId, MultipartFile file) throws IOException {
        Subject subject = findByIdBase(subjectId);
        String filepath = imageUploader.uploadImage(file, uploadDirectory);
        subject.setFilePath(filepath);
        return subjectRepository.save(subject);
    }

    // сущ ли subj с таким id - проверка для упд
    // право только на у а создал
    // без path variable и так не раб
    @Override
    public SubjectDTO updateById(Long subjectId, SubjectDTO newSubjectDTO) {
        Subject subject = findByIdBase(subjectId);
        if (subjectRepository.findBySubjectName(newSubjectDTO.getSubjectName()).isPresent())
            throw new EntityExistsException("Subject with name: " + newSubjectDTO.getSubjectName() + " already exists");
        Subject newSubject = toSubject(newSubjectDTO);
        newSubject.setSubjectId(subjectId);
        Subject subjectFromRepository = subjectRepository.save(newSubject);
        SubjectDTO subjectDTOFromRepository = modelMapper.map(subjectFromRepository, SubjectDTO.class);
        return subjectDTOFromRepository;
    }

    @Override
    public boolean deleteById(Long subjectId) {
        Subject subject = findByIdBase(subjectId);
        subjectRepository.delete(subject);
        return true;
    }

    public Subject toSubject(SubjectDTO subjectDTO) {
        Set<ConstraintViolation<SubjectDTO>> validationExceptions = validator.validate(subjectDTO);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        return subject;
    }
}