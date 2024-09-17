package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.SectionListRequest;
import io.github.kiransr99.parg.dto.request.SectionRequest;
import io.github.kiransr99.parg.dto.response.SectionResponse;
import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.Section;
import io.github.kiransr99.parg.repository.ClassRepository;
import io.github.kiransr99.parg.repository.SectionRepository;
import io.github.kiransr99.parg.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final ClassRepository classRepository;

    @Override
    public List<SectionResponse> saveSection(SectionListRequest request) {
        Class existingClass = classRepository.findById(request.getClassId())
                .filter(Class::isStatus)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND));
        List<Section> sections = request.getSection().stream()
                .map(sectionRequest -> {
                    Section section = new Section();
                    section.setName(sectionRequest.getName());
                    section.setSchool(existingClass.getSchool());
                    section.setClassName(existingClass);
                    return section;
                })
                .toList();
        List<Section> savedSections = sectionRepository.saveAll(sections);
        return savedSections.stream()
                .map(SectionResponse::new)
                .toList();
    }

    @Override
    public List<SectionResponse> getAllSections() {
        List<Section> sections = sectionRepository.findByStatusTrue();
        return sections.stream()
                .map(SectionResponse::new)
                .toList();
    }

    @Override
    public SectionResponse getSectionById(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .filter(Section::isStatus)
                .map(SectionResponse::new)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND));
    }

    @Override
    public SectionResponse updateSection(Long sectionId, SectionRequest request) {
        Section existingSection = sectionRepository.findById(sectionId)
                .filter(Section::isStatus)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND));
        existingSection.setName(request.getName());
        Section updatedSection = sectionRepository.save(existingSection);
        return new SectionResponse(updatedSection);
    }

    @Override
    public String deleteSection(Long sectionId) {
        Section existingSection = sectionRepository.findById(sectionId)
                .filter(Section::isStatus)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND));
        existingSection.setStatus(false);
        sectionRepository.save(existingSection);
        return SYSTEM_MESSAGE.SECTION_DELETED;
    }
}