package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.AcademicYearRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.AcademicYearResponse;
import io.github.kiransr99.parg.service.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URL_CONSTANTS.ACADEMIC_YEAR_URL)
@RequiredArgsConstructor
public class AcademicYearController extends BaseController {
    private final AcademicYearService academicYearService;

    @PostMapping(URL_CONSTANTS.SAVE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<AcademicYearResponse>> saveAcademicYear(@RequestBody AcademicYearRequest request) {
        return successResponse(academicYearService.createAcademicYear(request), SYSTEM_MESSAGE.ACADEMIC_YEAR_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_ACADEMIC_YEAR_BY_ID)
    public ResponseEntity<GlobalApiResponse<AcademicYearResponse>> getAcademicYearById(@PathVariable Long academicYearId) {
        return successResponse(academicYearService.getAcademicYear(academicYearId), SYSTEM_MESSAGE.ACADEMIC_YEAR_FETCHED_BY_ID);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<AcademicYearResponse>> updateAcademicYear(@PathVariable Long academicYearId, @RequestBody AcademicYearUpdateRequest academicYearUpdateRequest) {
        return successResponse(academicYearService.updateAcademicYear(academicYearId, academicYearUpdateRequest), SYSTEM_MESSAGE.ACADEMIC_YEAR_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<String>> deleteAcademicYear(@PathVariable Long academicYearId) {
        academicYearService.deleteAcademicYear(academicYearId);
        return successResponse(SYSTEM_MESSAGE.ACADEMIC_YEAR_DELETED);
    }

}
