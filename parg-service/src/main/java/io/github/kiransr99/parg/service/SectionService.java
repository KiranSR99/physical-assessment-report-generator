package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.SectionListRequest;
import io.github.kiransr99.parg.dto.request.SectionRequest;
import io.github.kiransr99.parg.dto.response.SectionResponse;

import java.util.List;

public interface SectionService {

    List<SectionResponse> saveSection(SectionListRequest request);

    List<SectionResponse> getAllSections();
    SectionResponse getSectionById(Long sectionId);
    SectionResponse updateSection(Long sectionId, SectionRequest request);
    String deleteSection(Long sectionId);
}