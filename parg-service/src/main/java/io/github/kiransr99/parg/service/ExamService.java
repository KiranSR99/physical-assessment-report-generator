package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;

import java.util.List;

public interface ExamService {
    ExamResponse createAcademicYear(ExamRequest examRequest);
    List<ExamResponse> getAllExams();
    ExamResponse getAcademicYear(Long id);
    ExamResponse updateAcademicYear(Long id, AcademicYearUpdateRequest academicYearUpdateRequest);
    void deleteAcademicYear(Long id);
}
