package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;
import io.github.kiransr99.parg.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URL_CONSTANTS.ACADEMIC_YEAR_URL)
@RequiredArgsConstructor
public class ExamController extends BaseController {
    private final ExamService examService;

    @PostMapping(URL_CONSTANTS.SAVE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> saveAcademicYear(@RequestBody ExamRequest request) {
        return successResponse(examService.createAcademicYear(request), SYSTEM_MESSAGE.ACADEMIC_YEAR_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_ACADEMIC_YEAR_BY_ID)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> getAcademicYearById(@PathVariable Long academicYearId) {
        return successResponse(examService.getAcademicYear(academicYearId), SYSTEM_MESSAGE.ACADEMIC_YEAR_FETCHED_BY_ID);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> updateAcademicYear(@PathVariable Long academicYearId, @RequestBody AcademicYearUpdateRequest academicYearUpdateRequest) {
        return successResponse(examService.updateAcademicYear(academicYearId, academicYearUpdateRequest), SYSTEM_MESSAGE.ACADEMIC_YEAR_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_ACADEMIC_YEAR)
    public ResponseEntity<GlobalApiResponse<String>> deleteAcademicYear(@PathVariable Long academicYearId) {
        examService.deleteAcademicYear(academicYearId);
        return successResponse(SYSTEM_MESSAGE.ACADEMIC_YEAR_DELETED);
    }

}
