package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.SubjectDTO;
import org.aquam.learnrest.model.Subject;
import org.aquam.learnrest.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/learn/subjects")
@RequiredArgsConstructor    // for all final fields
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return new ResponseEntity<>(subjectService.findAllDTO(), HttpStatus.OK);   // 200
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long subjectId) {
        return new ResponseEntity<>(subjectService.findByIdDTO(subjectId), HttpStatus.OK); // 200
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO newSubjectDTO) throws IOException {
        return new ResponseEntity<>(subjectService.createDTO(newSubjectDTO), HttpStatus.CREATED);  // 201
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload/{subjectId}")
    public ResponseEntity<Subject> addFile(@PathVariable Long subjectId, @RequestParam(value = "data") MultipartFile file) throws IOException {
        return new ResponseEntity<>(subjectService.addFile(subjectId, file), HttpStatus.OK);  // 200
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long subjectId, @RequestBody SubjectDTO newSubjectDTO) {
        return new ResponseEntity<>(subjectService.updateByIdDTO(subjectId, newSubjectDTO), HttpStatus.OK); // 200 for updates, 201 for created
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{subjectId}")  // HttpStatus.ACCEPTED = 202
    public ResponseEntity<Boolean> deleteSubjectById(@PathVariable Long subjectId) {
        return new ResponseEntity(subjectService.deleteById(subjectId), HttpStatus.OK);   // 204 - No Content
    }

}

/*
get, post = /api/articles
get, put, delete = /api/articles/{id}
 */

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long subjectId, SubjectDTO newSubjectDTO, MultipartFile file) throws IOException {
        return new ResponseEntity<>(subjectService.updateById(subjectId, newSubjectDTO, file), HttpStatus.OK); // 200 for updates, 201 for created
    }
     */

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Subject> createSubject(SubjectDTO newSubjectDTO, MultipartFile file) throws IOException {
        Subject s = subjectService.create(newSubjectDTO, file);
        return new ResponseEntity<>(subjectService.create(newSubjectDTO, file), HttpStatus.CREATED);  // 201
    }
     */