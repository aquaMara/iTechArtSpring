package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.dto.SectionDTO;
import org.aquam.learnrest.model.Section;
import org.aquam.learnrest.service.impl.SectionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learn/sections")
@RequiredArgsConstructor    // for all final fields
public class SectionController {

    private final SectionServiceImpl sectionService;

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<SectionDTO>> getAllSections() {
        return new ResponseEntity<>(sectionService.findAllDTO(), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionDTO> getSectionById(@PathVariable Long sectionId) {
        return new ResponseEntity<>(sectionService.findByIdDTO(sectionId), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<SectionDTO> createSection(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(sectionService.createDTO(sectionDTO), HttpStatus.CREATED);
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionDTO> updateSection(@PathVariable Long sectionId, @RequestBody SectionDTO newSectionDTO) {
        return new ResponseEntity<>(sectionService.updateByIdDTO(sectionId, newSectionDTO), HttpStatus.OK);
    }

    // +
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sectionId}")
    public ResponseEntity<Boolean> deleteSectionById(@PathVariable Long sectionId) {
        return new ResponseEntity(sectionService.deleteById(sectionId), HttpStatus.OK);
    }
}
