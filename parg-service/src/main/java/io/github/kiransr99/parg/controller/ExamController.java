package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.ExamUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;
import io.github.kiransr99.parg.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.EXAM_URL)
@RequiredArgsConstructor
public class ExamController extends BaseController {
    private final ExamService examService;

    @PostMapping(URL_CONSTANTS.SAVE_EXAM)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> saveAcademicYear(@RequestBody ExamRequest request) {
        return successResponse(examService.createExam(request), SYSTEM_MESSAGE.EXAM_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_EXAMS)
    public ResponseEntity<GlobalApiResponse<List<ExamResponse>>> getAllExam() {
        return successResponse(examService.getAllExams(), SYSTEM_MESSAGE.ALL_EXAM_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_EXAM_BY_ID)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> getAcademicYearById(@PathVariable Long examId) {
        return successResponse(examService.getExam(examId), SYSTEM_MESSAGE.EXAM_FETCHED_BY_ID);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_EXAMS_OF_SCHOOL)
    public ResponseEntity<GlobalApiResponse<List<ExamResponse>>> getAllExamsOfSchool(@PathVariable Long schoolId) {
        return successResponse(examService.getAllExamsOfSchool(schoolId), SYSTEM_MESSAGE.ALL_EXAM_FETCHED);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_EXAM)
    public ResponseEntity<GlobalApiResponse<ExamResponse>> updateAcademicYear(@PathVariable Long examId, @RequestBody ExamUpdateRequest examUpdateRequest) {
        return successResponse(examService.updateExam(examId, examUpdateRequest), SYSTEM_MESSAGE.EXAM_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_EXAM)
    public ResponseEntity<GlobalApiResponse<String>> deleteAcademicYear(@PathVariable Long examId) {
        examService.deleteExam(examId);
        return successResponse(SYSTEM_MESSAGE.EXAM_DELETED);
    }

}
