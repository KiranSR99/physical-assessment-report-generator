package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.SectionListRequest;
import io.github.kiransr99.parg.dto.request.SectionRequest;
import io.github.kiransr99.parg.dto.response.SectionResponse;
import io.github.kiransr99.parg.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.SECTION_URL)
public class SectionController extends BaseController {

    private final SectionService sectionService;

    @PostMapping(URL_CONSTANTS.SAVE_SECTION)
    public ResponseEntity<GlobalApiResponse<List<SectionResponse>>> saveSection(@Validated @RequestBody SectionListRequest request) {
        return successResponse(sectionService.saveSection(request), SYSTEM_MESSAGE.SECTION_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_SECTIONS)
    public ResponseEntity<GlobalApiResponse<List<SectionResponse>>> getAllSections() {
        return successResponse(sectionService.getAllSections(), SYSTEM_MESSAGE.SECTION_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_SECTION_BY_ID)
    public ResponseEntity<GlobalApiResponse<SectionResponse>> getSectionById(@PathVariable Long sectionId) {
        return successResponse(sectionService.getSectionById(sectionId), SYSTEM_MESSAGE.SECTION_FETCHED);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_SECTION)
    public ResponseEntity<GlobalApiResponse<SectionResponse>> updateSection(@PathVariable Long sectionId, @RequestBody SectionRequest request) {
        return successResponse(sectionService.updateSection(sectionId, request), SYSTEM_MESSAGE.SECTION_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_SECTION)
    public ResponseEntity<GlobalApiResponse<String>> deleteSection(@PathVariable Long sectionId) {
        return successResponse(sectionService.deleteSection(sectionId), SYSTEM_MESSAGE.SECTION_DELETED);
    }
}