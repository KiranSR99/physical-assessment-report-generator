package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.ExamUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.School;
import io.github.kiransr99.parg.repository.ExamRepository;
import io.github.kiransr99.parg.repository.SchoolRepository;
import io.github.kiransr99.parg.service.ExamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamImpl implements ExamService {
    private final ExamRepository examRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public ExamResponse createExam(ExamRequest examRequest) {
        log.info("Creating academic year: {}", examRequest);
        School school = schoolRepository.findById(examRequest.getSchoolId()).orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SCHOOL_NOT_FOUND));
        Exam exam = new Exam();
        exam.setYear(examRequest.getYear());
        exam.setExamName(examRequest.getExamName());
        exam.setSchool(school);
        examRepository.save(exam);
        return new ExamResponse(exam);
    }

    @Override
    public List<ExamResponse> getAllExams() {
        log.info("Getting all exams...");
        List<Exam> exams = examRepository.findAll();
        log.info("All exams found.");
        return exams.stream()
                .map(ExamResponse::new)
                .toList();
    }

    @Override
    public ExamResponse getExam(Long id) {
        log.info("Fetching academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        return new ExamResponse(exam);
    }

    @Override
    public ExamResponse updateExam(Long id, ExamUpdateRequest examUpdateRequest) {
        log.info("Updating academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        exam.setYear(examUpdateRequest.getYear());
        exam.setExamName(examUpdateRequest.getExamName());
        examRepository.save(exam);
        return new ExamResponse(exam);
    }

    @Override
    public void deleteExam(Long id) {
        log.info("Deleting academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        examRepository.delete(exam);
    }
}
