package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.ExamRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.ExamResponse;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.repository.ExamRepository;
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
    @Override
    public ExamResponse createAcademicYear(ExamRequest examRequest) {
        log.info("Creating academic year: {}", examRequest);
        Exam exam = new Exam();
        exam.setYear(examRequest.getYear());
        exam.setExamName(examRequest.getExamName());
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
    public ExamResponse getAcademicYear(Long id) {
        log.info("Fetching academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        return new ExamResponse(exam);
    }

    @Override
    public ExamResponse updateAcademicYear(Long id, AcademicYearUpdateRequest academicYearUpdateRequest) {
        log.info("Updating academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        exam.setYear(academicYearUpdateRequest.getYear());
        exam.setExamName(academicYearUpdateRequest.getExamName());
        examRepository.save(exam);
        return new ExamResponse(exam);
    }

    @Override
    public void deleteAcademicYear(Long id) {
        log.info("Deleting academic year with id: {}", id);
        Exam exam = examRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        examRepository.delete(exam);
    }
}
