package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.ExamUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;

import java.util.List;

public interface ExamService {
    ExamResponse createExam(ExamRequest examRequest);
    List<ExamResponse> getAllExams();
    ExamResponse getExam(Long id);
    ExamResponse updateExam(Long id, ExamUpdateRequest examUpdateRequest);
    void deleteExam(Long id);
}
